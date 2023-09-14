package pl.ms.projectoverview.app.exceptions;

public class UserAlreadyExistsException extends AppExceptions{
    public UserAlreadyExistsException() {
        super("User already exists");
    }
}
