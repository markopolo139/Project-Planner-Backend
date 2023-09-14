package pl.ms.projectoverview.app.exceptions;

public class InvalidJwtTokenException extends AppExceptions {
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
