package pl.ms.projectoverview.app.exceptions;

public class InvalidUserException extends AppExceptions{
    public InvalidUserException() {
        super("Invalid data for user creation");
    }
}
