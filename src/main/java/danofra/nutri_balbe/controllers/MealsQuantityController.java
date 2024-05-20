package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.enums.Unit_of_measure;
import danofra.nutri_balbe.payloads.MealsQuantityDTO;
import danofra.nutri_balbe.payloads.MealsQuantityResponseDTO;
import danofra.nutri_balbe.payloads.MealsResponseDTO;
import danofra.nutri_balbe.services.MealsQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mealsquantity")
public class MealsQuantityController {
    @Autowired
    private MealsQuantityService mealsQuantityService;

    @PostMapping("/me")
    public MealsQuantityResponseDTO saveMealsQuantity(@AuthenticationPrincipal User user, @RequestBody MealsQuantityDTO body) {
        return mealsQuantityService.save(user, body);
    }

    @GetMapping("/me")
    public List<MealsResponseDTO> findAllMealsQuantity(@AuthenticationPrincipal User user, @RequestParam int month, @RequestParam int year) {
        return mealsQuantityService.findByUserIdMonthAndYear(user, month, year);
    }

    @PutMapping("/me/{mealsquantityId}")
    public void updateMealsQuantity(@AuthenticationPrincipal User user,
                                    @PathVariable int mealsquantityId,
                                    @RequestParam int quantity,
                                    @RequestParam Unit_of_measure unitOfMeasure
    ) {
        mealsQuantityService.findByUserIdAndMealsquantityAndUpdateQuantity(user, mealsquantityId, quantity, unitOfMeasure);
    }

    @DeleteMapping("/me/{mealsquantityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMealsQuantity(@AuthenticationPrincipal User user, @PathVariable int mealsquantityId) {
        mealsQuantityService.findByUserIdAndMealquantityAndDelete(user, mealsquantityId);
    }


}
