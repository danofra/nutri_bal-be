package danofra.nutri_balbe.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        @NotEmpty(message = "Name cannot be empty")
        @NotNull(message = "Name cannot be null")
        String name,

        @NotEmpty(message = "Category cannot be empty")
        @NotNull(message = "Category cannot be null")
        String category,

        @NotEmpty(message = "Description cannot be empty")
        @NotNull(message = "Description cannot be null")
        String description,

        @NotNull(message = "Kcal cannot be null")
        @NotEmpty(message = "Kcal cannot be empty")
        int kcal,

        String image
) {
}
