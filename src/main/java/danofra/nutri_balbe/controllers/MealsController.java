package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.entities.Meals;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.payloads.MealsDTO;
import danofra.nutri_balbe.payloads.MealsSaveResponseDTO;
import danofra.nutri_balbe.services.MealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meals")
public class MealsController {

    @Autowired
    private MealsService mealsService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Meals getMeals(@RequestParam int userId) {
        return mealsService.findByUserId(userId);
    }

    @PostMapping("/me")
    public MealsSaveResponseDTO saveMeals(@AuthenticationPrincipal User user, @RequestBody MealsDTO mealsDTO) {
        return mealsService.save(mealsDTO, user);
    }

    @GetMapping("/me")
    public Meals getMyMeals(@AuthenticationPrincipal User user) {
        return mealsService.findByUserId(user.getId());
    }

}
