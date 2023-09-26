package pl.ms.projectoverview.app.exceptions;

public class UsernameAlreadyExistsException extends AppExceptions {
    public UsernameAlreadyExistsException() {
        super("Given username is being used");
    }
}
