package pl.ms.projectoverview.web.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.ms.projectoverview.web.validators.MyPasswordValidator;

public class CreateUserModel {

    @NotBlank
    private final String username;

    @NotNull
    @MyPasswordValidator
    private final String password;

    @NotBlank
    private final String email;

    public CreateUserModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
