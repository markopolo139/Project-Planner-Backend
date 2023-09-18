package pl.ms.projectoverview.web.controllers;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.ms.projectoverview.app.exceptions.UserAlreadyExistsException;
import pl.ms.projectoverview.app.services.UserService;
import pl.ms.projectoverview.web.models.request.CreateUserModel;

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
}
