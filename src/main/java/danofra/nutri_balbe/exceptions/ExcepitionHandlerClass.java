package danofra.nutri_balbe.exceptions;

import danofra.nutri_balbe.payloads.ErrorsResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExcepitionHandlerClass {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResponseDTO handleBadRequestException(BadRequestException ex) {
        if (ex.getErrorsList() != null) {
            String message = ex.getErrorsList().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            return new ErrorsResponseDTO(message, LocalDateTime.now());
        } else {
            return new ErrorsResponseDTO(ex.getMessage(), LocalDateTime.now());
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsResponseDTO handleUnauthorized(UnauthorizedException ex) {
        return new ErrorsResponseDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsResponseDTO handleNotFoundException(NotFoundException ex) {
        return new ErrorsResponseDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsResponseDTO handleAllExceptions(Exception ex) {
        return new ErrorsResponseDTO(ex.getMessage(), LocalDateTime.now());
    }
}
