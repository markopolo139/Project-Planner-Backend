package pl.ms.projectoverview.app.exceptions;

public class NotCurrentUserProjectException extends AppExceptions {
    public NotCurrentUserProjectException() {
        super("Project does not belong to currently logged in user");
    }
}
