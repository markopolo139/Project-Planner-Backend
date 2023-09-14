package pl.ms.projectoverview.app.services;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.ms.projectoverview.app.configuration.security.AppUserEntity;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;
import pl.ms.projectoverview.app.persistence.repositories.UserRepository;

public class AppUtils {
    public static int getUserId() {
        return ((AppUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public static UserEntity getCurrentUser(UserRepository userRepository) throws UserNotFoundException {
        return userRepository.findById(getUserId()).orElseThrow(UserNotFoundException::new);
    }
}
