package pl.ms.projectoverview.app.services;

import com.google.firebase.messaging.FirebaseMessaging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;
import pl.ms.projectoverview.app.persistence.repositories.UserRepository;

public class NotificationService {

    private final Logger mLogger = LogManager.getLogger();
    private final UserRepository mUserRepository;
    private final Integer userId = AppUtils.getUserId();

    public NotificationService(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public void addToken(String token) throws UserNotFoundException {
        UserEntity loggedInUser = AppUtils.getCurrentUser(mUserRepository);
        loggedInUser.getNotificationTokens().add(token);

        mUserRepository.save(loggedInUser);
    }

    public void removeToken(String token) throws UserNotFoundException {
        UserEntity loggedInUser = AppUtils.getCurrentUser(mUserRepository);
        loggedInUser.getNotificationTokens().remove(token);

        mUserRepository.save(loggedInUser);
    }

    public void removeNotifications() throws UserNotFoundException {
        UserEntity loggedInUser = AppUtils.getCurrentUser(mUserRepository);
        loggedInUser.getNotificationTokens().clear();

        mUserRepository.save(loggedInUser);
    }
}
