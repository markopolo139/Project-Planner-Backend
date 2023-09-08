package pl.ms.projectoverview.app.exceptions;

public class AppExceptions extends Exception {
    public AppExceptions() {
    }

    public AppExceptions(String message) {
        super(message);
    }

    public AppExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public AppExceptions(Throwable cause) {
        super(cause);
    }

    public AppExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
