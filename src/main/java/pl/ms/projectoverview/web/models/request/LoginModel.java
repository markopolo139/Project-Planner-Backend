package pl.ms.projectoverview.web.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.ms.projectoverview.web.validators.MyPasswordValidator;

public class LoginModel {

    @NotBlank
    private final String username;

    @NotNull
    @MyPasswordValidator
    private final String password;

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
