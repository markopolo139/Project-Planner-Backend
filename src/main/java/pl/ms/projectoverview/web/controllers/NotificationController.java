package pl.ms.projectoverview.web.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.services.NotificationService;

@RestController
@CrossOrigin
@Validated
public class NotificationController {

    private final NotificationService mNotificationService;

    public NotificationController(NotificationService notificationService) {
        mNotificationService = notificationService;
    }

    @PostMapping("/api/v1/notification/add")
    public void addToken(@RequestParam("token") @Valid @NotBlank String token) throws UserNotFoundException {
        mNotificationService.addToken(token);
    }

    @DeleteMapping("/api/v1/notification/remove")
    public void removeToken(@RequestParam("token") @Valid @NotBlank String token) throws UserNotFoundException {
        mNotificationService.removeToken(token);
    }

    @DeleteMapping("/api/v1/notification/remove/all")
    public void removeNotifications() throws UserNotFoundException {
        mNotificationService.removeNotifications();
    }
}
