package danofra.nutri_balbe.payloads;

import danofra.nutri_balbe.enums.Gender;
import danofra.nutri_balbe.enums.Physical_activity;
import danofra.nutri_balbe.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserDTO(
        @NotEmpty(message = "Name cannot be empty")
        @NotNull(message = "Name cannot be null")
        String name,
        @NotEmpty(message = "Surname cannot be empty")
        @NotNull(message = "Surname cannot be null")
        String surname,
        @NotEmpty(message = "Email cannot be empty")
        @NotNull(message = "Email cannot be null")
        @Email @Email(message = "Email is not valid")
        String email,
        @NotEmpty(message = "Password cannot be empty")
        String password,
        @NotNull(message = "Date of birth cannot be null")
        LocalDate date_of_birth,
        @NotNull(message = "Gender cannot be null")
        Gender gender,
        @NotNull(message = "Physical activity cannot be null")
        Physical_activity physical_activity,
        @NotEmpty(message = "Nationality cannot be empty")
        @NotNull(message = "Nationality cannot be null")
        String nationality,
        @NotEmpty(message = "City of residence cannot be empty")
        @NotNull(message = "City of residence cannot be null")
        String city_of_residence,
        Role role,
        String avatar
) {
}
