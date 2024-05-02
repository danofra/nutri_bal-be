package danofra.nutri_balbe.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int id) {
        super("Your search did not go well! The id " + id + " was not found!");
    }
}
