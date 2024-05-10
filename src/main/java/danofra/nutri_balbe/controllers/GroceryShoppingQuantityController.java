package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.entities.GroceryShoppingQuantity;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.payloads.GroceryShoppingQuantityResponseDTO;
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
    public GroceryShoppingQuantityResponseDTO saveGroceryShoppingQuantity(@AuthenticationPrincipal User user, @RequestParam int quantity, @RequestParam String productName) {
        return groceryShoppingQuantityService.save(user.getId(), quantity, productName);
    }

    @GetMapping("/me")
    public Page<GroceryShoppingQuantity> getGroceryShoppingQuantity(@AuthenticationPrincipal User user,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @RequestParam(defaultValue = "id") String sortBy) {
        return groceryShoppingQuantityService.findByUserId(user.getId(), page, size, sortBy);
    }

    @PutMapping("/me/{productName}")
    public void updateGroceryShoppingQuantity(@AuthenticationPrincipal User user,
                                              @PathVariable String productName,
                                              @RequestParam int quantity) {
        groceryShoppingQuantityService.findByUserIdAndProductNameAndUpdateQuantity(user.getId(), productName, quantity);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroceryShoppingQuantity(@AuthenticationPrincipal User user,
                                              @RequestParam String productName) {
        groceryShoppingQuantityService.findByUserIdAndProductNameAndDelete(user.getId(), productName);
    }
}
