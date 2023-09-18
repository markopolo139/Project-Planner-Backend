package pl.ms.projectoverview.app.schedule;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.ms.projectoverview.app.entitites.Project;
import pl.ms.projectoverview.app.converters.ProjectConverter;
import pl.ms.projectoverview.app.persistence.repositories.ProjectRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static pl.ms.projectoverview.app.converters.ProjectConverter.convertEntityToApp;

@Component
public class NotificationSchedule {
    private final Logger mLogger = LogManager.getLogger();
    private final ProjectRepository mProjectRepository;
    private final FirebaseMessaging mFirebaseMessaging;

    public NotificationSchedule(
            ProjectRepository projectRepository, FirebaseMessaging firebaseMessaging
    ) {
        mProjectRepository = projectRepository;
        mFirebaseMessaging = firebaseMessaging;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendNotifications() {
        mLogger.info("Entering scheduled task");
        List<Project> projects = convertEntityToApp(mProjectRepository.findAllForNotification());
        projects.forEach((it) -> {
            try {
                mFirebaseMessaging.send(createNotification(
                        it.getTitle(), LocalDateTime.now().until(it.getDeadline(), ChronoUnit.DAYS)
                ));
            } catch (FirebaseMessagingException e) {
                mLogger.error("Error occurred during sending notifications via firebase");
                throw new RuntimeException(e);
            }
        });
    }

    private Message createNotification(String title, long daysLeft) {
        String notificationBody = daysLeft == 0 ? "Today is deadline" : "Deadline approaching: " + daysLeft + " days left";

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(notificationBody)
                .build();

        return Message.builder().setNotification(notification).build();
    }
}
