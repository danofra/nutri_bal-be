package danofra.nutri_balbe.services;

import danofra.nutri_balbe.entities.Meals;
import danofra.nutri_balbe.entities.MealsQuantity;
import danofra.nutri_balbe.entities.Product;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.enums.Type_meals;
import danofra.nutri_balbe.exceptions.BadRequestException;
import danofra.nutri_balbe.payloads.MealsQuantityDTO;
import danofra.nutri_balbe.payloads.MealsQuantityResponseDTO;
import danofra.nutri_balbe.payloads.MealsResponseDTO;
import danofra.nutri_balbe.repositories.MealsDAO;
import danofra.nutri_balbe.repositories.MealsQuantityDAO;
import danofra.nutri_balbe.repositories.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MealsQuantityService {
    @Autowired
    private MealsQuantityDAO mealsQuantityDAO;

    @Autowired
    private MealsService mealsService;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private MealsDAO mealsDAO;

    public MealsQuantityResponseDTO save(User user, MealsQuantityDTO newMealsQuantity) {
        Optional<Meals> existingMeal = mealsQuantityDAO.findByDayMonthYearAndTypeMeals(newMealsQuantity.day(), newMealsQuantity.month(), newMealsQuantity.year(), Type_meals.valueOf(newMealsQuantity.type_meals()), user);
        Product product = productDAO.findByName(newMealsQuantity.productName()).orElseThrow(() -> new BadRequestException("Product " + newMealsQuantity.productName() + " does not exist!"));
        MealsQuantity currentMeals = null;
        if (existingMeal.isPresent()) {
            for (MealsQuantity mealsQuantity : existingMeal.get().getMealsQuantity()) {
                if (mealsQuantity.getProduct().getName().equals(product.getName())) {
                    currentMeals = mealsQuantity;
                }
            }
            if (currentMeals != null) {
                currentMeals.setQuantity(currentMeals.getQuantity() + newMealsQuantity.quantity());
                mealsQuantityDAO.save(currentMeals);
            } else {
                MealsQuantity mealsQuantity = new MealsQuantity();
                mealsQuantity.setMeals(existingMeal.get());
                mealsQuantity.setProduct(product);
                mealsQuantity.setQuantity(newMealsQuantity.quantity());
                mealsQuantityDAO.save(mealsQuantity);
                return new MealsQuantityResponseDTO(mealsQuantity.getQuantity(), mealsQuantity.getProduct().getName());
            }
        } else {
            Meals meals = new Meals();
            meals.setUser(user);
            meals.setType_meals(Type_meals.valueOf(newMealsQuantity.type_meals()));
            meals.setDay(newMealsQuantity.day());
            meals.setMonth(newMealsQuantity.month());
            meals.setYear(newMealsQuantity.year());
            MealsQuantity mealsQuantity = new MealsQuantity(newMealsQuantity.quantity(), product, meals);
            mealsDAO.save(meals);
            mealsQuantityDAO.save(mealsQuantity);
            return new MealsQuantityResponseDTO(mealsQuantity.getQuantity(), mealsQuantity.getProduct().getName());
        }
        return new MealsQuantityResponseDTO(currentMeals.getQuantity(), currentMeals.getProduct().getName());
    }

    public List<MealsResponseDTO> findByUserIdMonthAndYear(User user, int month, int year) {
        List<Meals> mealsList = this.mealsQuantityDAO.findByUserIdMonthAndYear(user, month, year);
        List<MealsResponseDTO> newListMeals = new ArrayList<>();
        for (Meals meals : mealsList) {
            MealsResponseDTO mealsResponseDTO = new MealsResponseDTO(meals.getType_meals(), meals.getDay(), meals.getMealsQuantity());
            newListMeals.add(mealsResponseDTO);
        }
        return newListMeals;
    }

    public MealsQuantityResponseDTO findByUserIdAndMealsquantityAndUpdateQuantity(User user, int mealsQuantityId, int quantity) {
        MealsQuantity mealsQuantity = mealsQuantityDAO.findById(mealsQuantityId).orElseThrow(() -> new BadRequestException("MealsQuantity " + mealsQuantityId + " does not exist!"));
        mealsQuantity.setQuantity(quantity);
        mealsQuantityDAO.save(mealsQuantity);
        return new MealsQuantityResponseDTO(mealsQuantity.getQuantity(), mealsQuantity.getProduct().getName());
    }

    public void findByUserIdAndMealquantityAndDelete(User user, int mealsQuantityId) {
        MealsQuantity mealsQuantity = mealsQuantityDAO.findById(mealsQuantityId).orElseThrow(() -> new BadRequestException("MealsQuantity " + mealsQuantityId + " does not exist!"));
        mealsQuantityDAO.delete(mealsQuantity);
    }
}

