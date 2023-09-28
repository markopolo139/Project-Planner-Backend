package pl.ms.projectoverview.web.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.server.PathParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.ms.projectoverview.app.exceptions.EmailAlreadyExistsException;
import pl.ms.projectoverview.app.exceptions.UserAlreadyExistsException;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.exceptions.UsernameAlreadyExistsException;
import pl.ms.projectoverview.app.services.UserService;
import pl.ms.projectoverview.web.models.request.CreateUserModel;
import pl.ms.projectoverview.web.validators.MyPasswordValidator;

@RestController
@CrossOrigin
@Validated
public class UserController {

    private final UserService mUserService;

    public UserController(UserService userService) {
        mUserService = userService;
    }

    @PostMapping("/api/v1/user/create")
    public void createUser(@RequestBody @Valid CreateUserModel model) throws UserAlreadyExistsException {
        mUserService.createUser(model.getUsername(), model.getPassword(), model.getEmail());
    }

    @DeleteMapping("/api/v1/user/delete")
    public void deleteUser() {
        mUserService.deleteUser();
    }

    @PutMapping("/api/v1/user/change/username")
    public void changeUsername(@RequestParam("username") @Valid @NotBlank String username) throws UserNotFoundException, UsernameAlreadyExistsException {
        mUserService.changeUsername(username);
    }

    @PutMapping("/api/v1/user/change/password")
    public void changePassword(@RequestParam("password") @Valid @MyPasswordValidator String password) throws UserNotFoundException {
        mUserService.changePassword(password);
    }

    @PutMapping("/api/v1/user/change/email")
    public void changeEmail(@RequestParam("email") @Valid @Email String email) throws UserNotFoundException, EmailAlreadyExistsException {
        mUserService.changeEmail(email);
    }

    @GetMapping("/api/v1/user/email")
    public String getEmail() throws UserNotFoundException {
        return mUserService.getEmail();
    }

}
