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

@Service
public class FoodStorageQuantityService {

    @Autowired
    private FoodStorageQuantityDAO foodStorageQuantityDAO;

    @Autowired
    private FoodStorageService foodStorageService;

    @Autowired
    private ProductDAO productDAO;

    public FoodStorageQuantityResponseDTO save(int userId, int quantity, int productId) {
        FoodStorage foodStorage = foodStorageService.findByUserId(userId);
        Product product = productDAO.findById(productId).orElseThrow(() -> new BadRequestException("Product whit id " + productId + " does not exist!"));
        FoodStorageQuantity foodStorageQuantity = new FoodStorageQuantity(quantity, product, foodStorage);
        foodStorageQuantityDAO.save(foodStorageQuantity);
        return new FoodStorageQuantityResponseDTO(foodStorageQuantity.getQuantity(), foodStorageQuantity.getProduct().getName());
    }

    public Page<FoodStorageQuantity> findByUserId(int userId, int page, int size, String sortBy) {
        if (size > 10) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.foodStorageQuantityDAO.findByFoodStorageIdPage(foodStorageService.findByUserId(userId).getId(), pageable);
    }

    public FoodStorageQuantityResponseDTO findByUserIdAndProductIdAndUpdateQuantity(int userId, int productId, int quantity) {
        FoodStorage foodStorage = foodStorageService.findByUserId(userId);
        Product product = productDAO.findById(productId).orElseThrow(() -> new BadRequestException("Product whit id " + productId + " does not exist!"));
        List<FoodStorageQuantity> foodStorageQuantity = this.foodStorageQuantityDAO.findByFoodStorageId(foodStorage.getId());
        FoodStorageQuantityResponseDTO response = null;
        for (FoodStorageQuantity foodStorageQuantity1 : foodStorageQuantity) {
            if (foodStorageQuantity1.getProduct().getId() == productId) {
                foodStorageQuantity1.setQuantity(quantity);
                foodStorageQuantityDAO.save(foodStorageQuantity1);
                response = new FoodStorageQuantityResponseDTO(foodStorageQuantity1.getQuantity(), foodStorageQuantity1.getProduct().getName());
            }
        }
        return response;
    }

    public void findByUserIdAndProductIdAndDelete(int userId, int productId) {
        FoodStorage foodStorage = foodStorageService.findByUserId(userId);
        Product product = productDAO.findById(productId).orElseThrow(() -> new BadRequestException("Product whit id " + productId + " does not exist!"));
        List<FoodStorageQuantity> foodStorageQuantity = this.foodStorageQuantityDAO.findByFoodStorageId(foodStorage.getId());
        for (FoodStorageQuantity foodStorageQuantity1 : foodStorageQuantity) {
            if (foodStorageQuantity1.getProduct().getId() == productId) {
                foodStorageQuantityDAO.delete(foodStorageQuantity1);
            }
        }
    }
}
