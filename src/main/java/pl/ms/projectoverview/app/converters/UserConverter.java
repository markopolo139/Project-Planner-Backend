package pl.ms.projectoverview.app.converters;

import org.springframework.stereotype.Component;
import pl.ms.projectoverview.app.configuration.security.AppUserEntity;
import pl.ms.projectoverview.app.exceptions.InvalidUserException;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class UserConverter implements Converter<AppUserEntity, UserEntity, Object> {
    @Override
    public AppUserEntity convertEntityToApp(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        try {
            return AppUserEntity.customBuilder()
                    .setId(userEntity.getUserId())
                    .setUsername(userEntity.getUsername())
                    .setPassword(userEntity.getPassword())
                    .setAuthorities(userEntity.getRoles().toArray(new String[]{}))
                    .setEmail(userEntity.getEmail())
                    .build();
        } catch (InvalidUserException ex) {
            return null;
        }
    }

    @Override
    public UserEntity convertToEntity(AppUserEntity appEntity) {
        if (appEntity == null) {
            return null;
        }

        return new UserEntity(
                appEntity.getId(), appEntity.getUsername(), appEntity.getPassword(), appEntity.getEmail(), false,
                appEntity.getAuthorities().stream().map(
                        it -> it.getAuthority().replace("ROLE_", "")
                ).collect(Collectors.toSet()),
                Collections.emptySet()
        );
    }

    @Override
    public AppUserEntity convertModelToApp(Object entity) {
        return null;
    }

    @Override
    public Object convertToModel(AppUserEntity appEntity) {
        return null;
    }
}
