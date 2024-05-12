package danofra.nutri_balbe.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MealsDTO(
        @NotEmpty(message = "Type of meals is required. Please enter a valid type of meals")
        String type_meals,
        @NotNull(message = "Day is required. Please enter a valid day")
        @Size(message = "Day should be between 1 and 31", min = 1, max = 31)
        int day,
        @NotNull(message = "Month is required. Please enter a valid month")
        @Size(message = "Month should be between 1 and 12", min = 1, max = 12)
        int month,
        @NotNull(message = "Year is required")
        @NotEmpty(message = "Year is required. Please enter a valid year")
        int year,
        UserDTO user
) {
}
