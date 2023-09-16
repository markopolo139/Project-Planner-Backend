package pl.ms.projectoverview.app.exceptions;

public class TitleNotFoundException extends AppExceptions{
    public TitleNotFoundException() {
        super("Project (plan) with given title does not exists");
    }
}
