package pl.ms.projectoverview.app.exceptions;

public class UserNotFoundException extends AppExceptions{
    public UserNotFoundException() {
        super("User not found");
    }
}
