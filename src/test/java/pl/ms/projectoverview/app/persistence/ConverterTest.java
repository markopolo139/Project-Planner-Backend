package pl.ms.projectoverview.app.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.ms.projectoverview.app.entitites.Project;
import pl.ms.projectoverview.app.entitites.ProjectPlan;
import pl.ms.projectoverview.app.entitites.ProjectStatus;
import pl.ms.projectoverview.app.converters.ProjectConverter;
import pl.ms.projectoverview.app.converters.ProjectPlanConverter;
import pl.ms.projectoverview.app.persistence.entities.ProjectEntity;
import pl.ms.projectoverview.app.persistence.entities.ProjectPlanEntity;

import java.time.LocalDateTime;
import java.util.Set;

import static pl.ms.projectoverview.app.converters.ProjectConverter.convertEntityToApp;
import static pl.ms.projectoverview.app.converters.ProjectPlanConverter.convertEntityToApp;
import static pl.ms.projectoverview.app.converters.ProjectConverter.convertToEntity;
import static pl.ms.projectoverview.app.converters.ProjectPlanConverter.convertToEntity;

public class ConverterTest {

    private final LocalDateTime date = LocalDateTime.now();

    private final ProjectEntity projectEntity = new ProjectEntity(
            0,"link1","title1","123","language", date,
            date, true, ProjectStatus.ACTIVE, Set.of("feature"), Set.of("goal"),
            Set.of("tech"), null
    );

    private final Project project = new Project(
            0, "link1","title1","123","language", date,
            date, true, ProjectStatus.ACTIVE, Set.of("feature"), Set.of("goal"),
            Set.of("tech")
    );

    private final ProjectPlanEntity projectPlanEntity = new ProjectPlanEntity(
            0,"title1","language",  Set.of("feature"), Set.of("goal"), Set.of("point"), null
    );

    private final ProjectPlan projectPlan = new ProjectPlan(
            0,"title1","language",  Set.of("feature"), Set.of("goal"), Set.of("point")
    );


    @Test
    void testProjectConverter() {
        Project convertedApp = convertEntityToApp(projectEntity);
        ProjectEntity convertedEntity = convertToEntity(project);

        Assertions.assertEquals(project, convertedApp);
        Assertions.assertEquals(projectEntity, convertedEntity);
    }

    @Test
    void testProjectPlanConverter() {
        ProjectPlan convertedApp = convertEntityToApp(projectPlanEntity);
        ProjectPlanEntity convertedEntity = convertToEntity(projectPlan);

        Assertions.assertEquals(projectPlan, convertedApp);
        Assertions.assertEquals(projectPlanEntity, convertedEntity);
    }
}
