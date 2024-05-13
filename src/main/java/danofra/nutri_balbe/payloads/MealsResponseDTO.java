package danofra.nutri_balbe.payloads;

import danofra.nutri_balbe.entities.MealsQuantity;
import danofra.nutri_balbe.enums.Type_meals;

import java.util.List;

public record MealsResponseDTO(Type_meals type_meals, int day, List<MealsQuantity> mealsQuantity) {
}
