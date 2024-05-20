package danofra.nutri_balbe.payloads;

import danofra.nutri_balbe.enums.Unit_of_measure;

public record MealsQuantityDTO(
        String productName,
        int quantity,
        Unit_of_measure unit_of_measure,
        String type_meals,
        int day,
        int month,
        int year
) {
}
