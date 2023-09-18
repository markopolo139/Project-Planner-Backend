package pl.ms.projectoverview.web.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.ms.projectoverview.app.services.AuthenticationService;
import pl.ms.projectoverview.web.models.request.LoginModel;

@RestController
@CrossOrigin
@Validated
public class AuthenticationController {

    private final AuthenticationService mAuthenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        mAuthenticationService = authenticationService;
    }

    @PostMapping("/auth")
    public String authenticate(@RequestBody @Valid LoginModel loginModel) {
        return mAuthenticationService.authenticate(loginModel.getUsername(), loginModel.getPassword());
    }
}
