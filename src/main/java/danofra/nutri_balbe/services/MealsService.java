package danofra.nutri_balbe.services;


import danofra.nutri_balbe.entities.Meals;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.enums.Type_meals;
import danofra.nutri_balbe.exceptions.BadRequestException;
import danofra.nutri_balbe.payloads.MealsDTO;
import danofra.nutri_balbe.payloads.MealsQuantityResponseDTO;
import danofra.nutri_balbe.payloads.MealsSaveResponseDTO;
import danofra.nutri_balbe.repositories.MealsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealsService {
    @Autowired
    private MealsDAO mealsDAO;

    public MealsSaveResponseDTO save(MealsDTO newMeals, User userId) {
        Meals meals = new Meals();
        meals.setUser(userId);
        meals.setType_meals(Type_meals.valueOf(newMeals.type_meals()));
        meals.setDay(newMeals.day());
        meals.setMonth(newMeals.month());
        meals.setYear(newMeals.year());
        mealsDAO.save(meals);
        return new MealsSaveResponseDTO(userId.getName(), userId.getSurname(), meals.getId(), meals.getType_meals(), meals.getDay(), meals.getMonth(), meals.getYear());
    }

    public Meals findByUserId(int userId) {
        return mealsDAO.findByUserId(userId).orElseThrow(() -> new BadRequestException("Meals whit user " + userId + " does not exist!"));
    }

    public List<MealsQuantityResponseDTO> findAllMealsWithQuantity() {
        return null;
    }
}
