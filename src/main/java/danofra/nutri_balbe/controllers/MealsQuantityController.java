package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.payloads.MealsQuantityDTO;
import danofra.nutri_balbe.payloads.MealsQuantityResponseDTO;
import danofra.nutri_balbe.services.MealsQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mealsquantity")
public class MealsQuantityController {
    @Autowired
    private MealsQuantityService mealsQuantityService;

    @PostMapping("/me")
    public MealsQuantityResponseDTO saveMealsQuantity(@AuthenticationPrincipal User user, @RequestBody MealsQuantityDTO body) {
        return mealsQuantityService.save(user, body);
    }

//    @GetMapping("/me")
//    public MealsQuantityResponseDTO getMealsQuantity(@AuthenticationPrincipal User user, @Validated @RequestBody MealsQuantityDTO body) {
//        return mealsQuantityService.findByUserIdAndMealQuantity(user, body);
//    }

    @PutMapping("/me")
    public void updateMealsQuantity(@AuthenticationPrincipal User user,
                                    @RequestBody MealsQuantityDTO body,
                                    @RequestParam int quantity) {
        mealsQuantityService.findByUserIdAndMealsquantityAndUpdateQuantity(user, body, quantity);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMealsQuantity(@AuthenticationPrincipal User user, @RequestBody MealsQuantityDTO body) {
        mealsQuantityService.findByUserIdAndMealquantityAndDelete(user, body);
    }


}
