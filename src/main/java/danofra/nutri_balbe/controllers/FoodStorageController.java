package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.entities.FoodStorage;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.payloads.FoodStorageResponseDTO;
import danofra.nutri_balbe.services.FoodStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foodstorage")
public class FoodStorageController {

    @Autowired
    private FoodStorageService foodStorageService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public FoodStorage getFoodStorage(@RequestParam int userId) {
        return foodStorageService.findByUserId(userId);
    }

    @PostMapping("/me")
    public FoodStorageResponseDTO saveFoodStorage(@AuthenticationPrincipal User user) {
        return foodStorageService.save(user);
    }

    @GetMapping("/me")
    public FoodStorage getMyFoodStorage(@AuthenticationPrincipal User user) {
        return foodStorageService.findByUserId(user.getId());
    }
}
