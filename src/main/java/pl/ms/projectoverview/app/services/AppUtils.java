package pl.ms.projectoverview.app.services;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.ms.projectoverview.app.configuration.security.AppUserEntity;

public class AppUtils {
    public static int getUserId() {
        return ((AppUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
