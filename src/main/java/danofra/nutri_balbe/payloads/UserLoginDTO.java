package danofra.nutri_balbe.payloads;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginDTO(
        @NotEmpty(message = "Inserisci l'email")
        String email,
        @NotEmpty(message = "Inserisci la password")
        String password) {
}
