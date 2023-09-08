package pl.ms.projectoverview.app.services;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.ms.projectoverview.app.configuration.security.UserEntity;

public class AppUtils {
    public static int getUserId() {
        return ((UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
