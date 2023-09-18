package pl.ms.projectoverview.web.controllers;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ms.projectoverview.app.exceptions.InvalidJwtTokenException;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.password.recovery.PasswordRecoveryService;
import pl.ms.projectoverview.web.validators.MyPasswordValidator;

@RestController
@CrossOrigin
@Validated
public class PasswordRecoveryController {

    private final PasswordRecoveryService mPasswordRecoveryService;

    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        mPasswordRecoveryService = passwordRecoveryService;
    }

    @PostMapping("/recoverPwd")
    public void sendMessage(@RequestParam("email") @Valid @Email String email)
            throws UserNotFoundException, MessagingException {
        mPasswordRecoveryService.sendMessage(email);
    }

    @PostMapping("/api/v1/change/password")
    public void changePassword(@RequestParam("newPassword") @Valid @MyPasswordValidator String newPassword)
            throws UserNotFoundException, InvalidJwtTokenException {
        mPasswordRecoveryService.changePassword(newPassword);
    }
}
