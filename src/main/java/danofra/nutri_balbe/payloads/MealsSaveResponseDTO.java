package danofra.nutri_balbe.payloads;

import danofra.nutri_balbe.enums.Type_meals;

public record MealsSaveResponseDTO(String name, String surname, int id, Type_meals type_meals, int day, int month,
                                   int year) {
}
