package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.entities.GroceryShoppingQuantity;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.services.GroceryShoppingQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("groceryshoppingquantity")
public class GroceryShoppingQuantityController {
    @Autowired
    private GroceryShoppingQuantityService groceryShoppingQuantityService;

    @PostMapping("/me")
    public void saveGroceryShoppingQuantity(@AuthenticationPrincipal User user, @RequestParam int quantity, @RequestParam int productId) {
        groceryShoppingQuantityService.save(user.getId(), quantity, productId);
    }

    @GetMapping("/me")
    public Page<GroceryShoppingQuantity> getGroceryShoppingQuantity(@AuthenticationPrincipal User user,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @RequestParam(defaultValue = "id") String sortBy) {
        return groceryShoppingQuantityService.findByUserId(user.getId(), page, size, sortBy);
    }

    @PatchMapping("/me")
    public void updateGroceryShoppingQuantity(@AuthenticationPrincipal User user,
                                              @RequestParam int productId,
                                              @RequestParam int quantity) {
        groceryShoppingQuantityService.findByUserIdAndProductIdAndUpdateQuantity(user.getId(), productId, quantity);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroceryShoppingQuantity(@AuthenticationPrincipal User user,
                                              @RequestParam int productId) {
        groceryShoppingQuantityService.findByUserIdAndProductIdAndDelete(user.getId(), productId);
    }
}
