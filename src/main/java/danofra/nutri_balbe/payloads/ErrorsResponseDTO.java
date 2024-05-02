package danofra.nutri_balbe.payloads;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(
        String message,
        LocalDateTime timestamp
) {
}
