package pl.ms.projectoverview.app.exceptions;

public class NotificationTokenAlreadyAddedException extends AppExceptions {
    public NotificationTokenAlreadyAddedException() {
        super("Given notification token is already added for current user");
    }
}
