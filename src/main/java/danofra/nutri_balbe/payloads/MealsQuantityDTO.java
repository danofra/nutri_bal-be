package danofra.nutri_balbe.payloads;

public record MealsQuantityDTO(
        String productName,
        int quantity,
        String type_meals,
        int day,
        int month,
        int year
) {
}
