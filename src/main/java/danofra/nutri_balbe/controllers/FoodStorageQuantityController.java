package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.entities.FoodStorageQuantity;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.services.FoodStorageQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foodstoragequantity")
public class FoodStorageQuantityController {
    @Autowired
    private FoodStorageQuantityService foodStorageQuantityService;

    @PostMapping("/me")
    public void saveFoodStorageQuantity(@AuthenticationPrincipal User user, @RequestParam int quantity, @RequestParam String productName) {
        foodStorageQuantityService.save(user.getId(), quantity, productName);
    }

    @GetMapping("/me")
    public Page<FoodStorageQuantity> getMyFoodStorageQuantity(@AuthenticationPrincipal User user,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "1000") int size,
                                                              @RequestParam(defaultValue = "id") String sortBy) {
        return foodStorageQuantityService.findByUserId(user.getId(), page, size, sortBy);
    }

    @PutMapping("/me/{productName}")
    public void updateFoodStorageQuantity(@AuthenticationPrincipal User user,
                                          @PathVariable String productName,
                                          @RequestParam int quantity) {
        foodStorageQuantityService.findByUserIdAndProductNameAndUpdateQuantity(user.getId(), productName, quantity);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodStorageQuantity(@AuthenticationPrincipal User user,
                                          @RequestParam String productName) {
        foodStorageQuantityService.findByUserIdAndProductIdAndDelete(user.getId(), productName);
    }
}
