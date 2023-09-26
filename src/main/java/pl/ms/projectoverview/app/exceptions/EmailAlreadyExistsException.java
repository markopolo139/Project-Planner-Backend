package pl.ms.projectoverview.app.exceptions;

public class EmailAlreadyExistsException extends AppExceptions {
    public EmailAlreadyExistsException() {
        super("Given email is being used");
    }
}
