package danofra.nutri_balbe.payloads;

import danofra.nutri_balbe.enums.Unit_of_measure;

public record MealsQuantityResponseDTO(int quantity, Unit_of_measure unite_of_measure, String productName) {
}
