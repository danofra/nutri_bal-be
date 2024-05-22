package danofra.nutri_balbe.services;

import danofra.nutri_balbe.entities.FoodStorage;
import danofra.nutri_balbe.entities.FoodStorageQuantity;
import danofra.nutri_balbe.entities.Product;
import danofra.nutri_balbe.exceptions.BadRequestException;
import danofra.nutri_balbe.payloads.FoodStorageQuantityResponseDTO;
import danofra.nutri_balbe.repositories.FoodStorageQuantityDAO;
import danofra.nutri_balbe.repositories.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FoodStorageQuantityService {

    @Autowired
    private FoodStorageQuantityDAO foodStorageQuantityDAO;

    @Autowired
    private FoodStorageService foodStorageService;

    @Autowired
    private ProductDAO productDAO;

    public FoodStorageQuantityResponseDTO save(int userId, int quantity, String productName) {
        FoodStorage foodStorage = foodStorageService.findByUserId(userId);
        Product product = productDAO.findById(productName).orElseThrow(() -> new BadRequestException("Product with " + productName + " does not exist!"));
        Optional<FoodStorageQuantity> existingFoodStorageQuantity = foodStorageQuantityDAO.findByFoodStorageIdAndProductName(foodStorage.getId(), productName);
        FoodStorageQuantity foodStorageQuantity;
        if (existingFoodStorageQuantity.isPresent()) {
            foodStorageQuantity = existingFoodStorageQuantity.get();
            foodStorageQuantity.setQuantity(foodStorageQuantity.getQuantity() + quantity);
        } else {
            foodStorageQuantity = new FoodStorageQuantity(quantity, product, foodStorage);
        }
        foodStorageQuantityDAO.save(foodStorageQuantity);
        return new FoodStorageQuantityResponseDTO(foodStorageQuantity.getQuantity(), foodStorageQuantity.getProduct().getName());
    }

    public Page<FoodStorageQuantity> findByUserId(int userId, int page, int size, String sortBy) {
        if (size > 1000) size = 1000;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.foodStorageQuantityDAO.findByFoodStorageId(foodStorageService.findByUserId(userId).getId(), pageable);
    }

    public FoodStorageQuantityResponseDTO findByUserIdAndProductNameAndUpdateQuantity(int userId, String productName, int quantity) {
        FoodStorage foodStorage = foodStorageService.findByUserId(userId);
        Product product = productDAO.findById(productName).orElseThrow(() -> new BadRequestException("Product whit id " + productName + " does not exist!"));
        List<FoodStorageQuantity> foodStorageQuantity = this.foodStorageQuantityDAO.findByFoodStorageId(foodStorage.getId());
        FoodStorageQuantityResponseDTO response = null;
        for (FoodStorageQuantity foodStorageQuantity1 : foodStorageQuantity) {
            if ((foodStorageQuantity1.getProduct().getName() == productName)) {
                foodStorageQuantity1.setQuantity(quantity);
                foodStorageQuantityDAO.save(foodStorageQuantity1);
                response = new FoodStorageQuantityResponseDTO(foodStorageQuantity1.getQuantity(), foodStorageQuantity1.getProduct().getName());
            }
        }
        return response;
    }

    public void findByUserIdAndProductIdAndDelete(int userId, String productName) {
        FoodStorage foodStorage = foodStorageService.findByUserId(userId);
        Product product = productDAO.findById(productName).orElseThrow(() -> new BadRequestException("Product whit id " + productName + " does not exist!"));
        List<FoodStorageQuantity> foodStorageQuantity = this.foodStorageQuantityDAO.findByFoodStorageId(foodStorage.getId());
        for (FoodStorageQuantity foodStorageQuantity1 : foodStorageQuantity) {
            if (Objects.equals(foodStorageQuantity1.getProduct().getName(), productName)) {
                foodStorageQuantityDAO.delete(foodStorageQuantity1);
            }
        }
    }
}
