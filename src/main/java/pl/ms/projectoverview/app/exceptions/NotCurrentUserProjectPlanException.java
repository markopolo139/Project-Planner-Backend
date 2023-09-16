package pl.ms.projectoverview.app.exceptions;

public class NotCurrentUserProjectPlanException extends AppExceptions {
    public NotCurrentUserProjectPlanException() {
        super("Project plan does not belong to currently logged in user");
    }
}
