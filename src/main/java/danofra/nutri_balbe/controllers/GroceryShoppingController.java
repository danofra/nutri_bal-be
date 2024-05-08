package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.entities.GroceryShopping;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.payloads.GroceryShoppingResponseDTO;
import danofra.nutri_balbe.services.GroceryShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groceryshopping")
public class GroceryShoppingController {
    @Autowired
    private GroceryShoppingService groceryShoppingService;


    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public GroceryShopping getGroceryShopping(@RequestParam int userId) {
        return groceryShoppingService.findByUserId(userId);
    }

    @PostMapping("/me")
    public GroceryShoppingResponseDTO saveGroceryShopping(@AuthenticationPrincipal User user) {
        return groceryShoppingService.save(user);
    }

    @GetMapping("/me")
    public GroceryShopping getMyGroceryShopping(@AuthenticationPrincipal User user) {
        return groceryShoppingService.findByUserId(user.getId());
    }


}
