package pl.ms.projectoverview.app.persistence;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.ms.projectoverview.app.entitites.ProjectStatus;
import pl.ms.projectoverview.app.persistence.entities.ProjectEntity;
import pl.ms.projectoverview.app.persistence.entities.ProjectPlanEntity;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;
import pl.ms.projectoverview.app.persistence.repositories.ProjectPlanRepository;
import pl.ms.projectoverview.app.persistence.repositories.ProjectRepository;
import pl.ms.projectoverview.app.persistence.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class GeneralPersistenceTest {

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private ProjectRepository mProjectRepository;

    @Autowired
    private ProjectPlanRepository mProjectPlanRepository;

    @Order(1)
    @Test
    void saveUserTest() {
        UserEntity user1 = new UserEntity(
                0,"Marek", "123", "marek@seget@wp.pl",
                true, new HashSet<>() {{ add("ADMIN"); add("USER"); }}, Set.of("token1")
        );

        UserEntity user2 = new UserEntity(
                0,"Tomasz", "123", "tomasz@seget@wp.pl",
                false, new HashSet<>() {{ add("USER"); }}, Collections.emptySet()
        );

        UserEntity user3 = new UserEntity(
                0,"Kamil", "123", "kamil@seget@wp.pl",
                true, new HashSet<>() {{ add("USER"); }}, Set.of("token2")
        );

        mUserRepository.saveAll(Arrays.asList(user1, user2, user3));
    }

    @Order(2)
    @Test
    void testSavingAndAddingProjects() {
        Assertions.assertTrue(mUserRepository.findByUsername("Marek").isPresent());
        UserEntity user = mUserRepository.findByUsername("Marek").get();

        Assertions.assertTrue(mUserRepository.findByUsername("Kamil").isPresent());
        UserEntity user2 = mUserRepository.findByUsername("Kamil").get();

        ProjectEntity project1 = new ProjectEntity(
                0,"link1","title1","123","language", LocalDateTime.now(),
                LocalDateTime.now(), true, ProjectStatus.ACTIVE, Set.of("feature"), Set.of("goal"),
                Set.of("tech"), null
        );

        ProjectEntity project2 = new ProjectEntity(
                0,"link2","title2","123","language", null,
                LocalDateTime.now(), false, ProjectStatus.ACTIVE, Set.of("feature"), Set.of("goal"),
                Set.of("tech"), null
        );

        ProjectEntity project3 = new ProjectEntity(
                0,"link3","title3","123","language", LocalDateTime.now(),
                LocalDateTime.now(), true, ProjectStatus.ACTIVE, Set.of("feature"), Set.of("goal"),
                Set.of("tech"), null
        );

        user = mUserRepository.fetchProjects(user.getUserId());
        user2 = mUserRepository.fetchProjects(user2.getUserId());

        user.addProject(project1);
        user.addProject(project2);
        user2.addProject(project3);

        mUserRepository.saveAll(List.of(user, user2));
    }

    @Order(3)
    @Test
    void testSavingAndAddingProjectPlans() {
        Assertions.assertTrue(mUserRepository.findByUsername("Marek").isPresent());
        UserEntity user = mUserRepository.findByUsername("Marek").get();

        Assertions.assertTrue(mUserRepository.findByUsername("Kamil").isPresent());
        UserEntity user2 = mUserRepository.findByUsername("Kamil").get();

        ProjectPlanEntity projectPlan1 = new ProjectPlanEntity(
                0,"title1","language",  Set.of("feature"), Set.of("goal"), Set.of("point"), null
        );

        ProjectPlanEntity projectPlan2 = new ProjectPlanEntity(
                0,"title2","language", Set.of("feature"), Set.of("goal"), Set.of("point"), null
        );

        ProjectPlanEntity projectPlan3 = new ProjectPlanEntity(
                0,"title3","language2", Set.of("feature"), Set.of("goal"), Set.of("point"), null
        );


        user = mUserRepository.fetchProjectPlans(user.getUserId());
        user2 = mUserRepository.fetchProjectPlans(user2.getUserId());

        user.addProjectPlan(projectPlan1);
        user.addProjectPlan(projectPlan3);
        user2.addProjectPlan(projectPlan2);

        mUserRepository.saveAll(List.of(user, user2));
    }

    @Order(4)
    @Test
    void filterQueryForProjectTest() {
        Assertions.assertTrue(mUserRepository.findByUsername("Marek").isPresent());
        Integer id1 = mUserRepository.findByUsername("Marek").get().getUserId();
        Assertions.assertTrue(mUserRepository.findByUsername("Kamil").isPresent());
        Integer id2 = mUserRepository.findByUsername("Kamil").get().getUserId();
        List<ProjectEntity> projects = mProjectRepository.filterQuery(
                id1, null, null , null, true, null
        );

        Assertions.assertEquals(1, projects.size());
        Assertions.assertNotNull(projects.get(0));

        projects = mProjectRepository.filterQuery(
                id1, "language", null , null, null, null
        );
        Assertions.assertEquals(2, projects.size());

        projects = mProjectRepository.filterQuery(
                id2, "language", null , null, null, null
        );
        Assertions.assertEquals(1, projects.size());

        projects = mProjectRepository.filterQuery(
                id2, "adsasd", null , null, null, null
        );
        Assertions.assertEquals(0, projects.size());
    }

    @Order(5)
    @Test
    void filterQueryForProjectPlanTest() {
        Assertions.assertTrue(mUserRepository.findByUsername("Marek").isPresent());
        Integer id1 = mUserRepository.findByUsername("Marek").get().getUserId();
        Assertions.assertTrue(mUserRepository.findByUsername("Kamil").isPresent());
        Integer id2 = mUserRepository.findByUsername("Kamil").get().getUserId();
        List<ProjectPlanEntity> projectPlans = mProjectPlanRepository.filterQuery(
                id1, null
        );

        Assertions.assertEquals(2, projectPlans.size());
        Assertions.assertNotNull(projectPlans.get(0));

        projectPlans = mProjectPlanRepository.filterQuery(
                id1, "language"
        );
        Assertions.assertEquals(1, projectPlans.size());

        projectPlans = mProjectPlanRepository.filterQuery(
                id2, "language"
        );
        Assertions.assertEquals(1, projectPlans.size());

        projectPlans = mProjectPlanRepository.filterQuery(
                id2, "adsasd"
        );
        Assertions.assertEquals(0, projectPlans.size());
    }

    @Order(6)
    @Test
    void testGettingProjectsWithDeadlinesAndNotificationEnabled() {
        List<ProjectEntity> projects = mProjectRepository.findAllForNotification();
        Assertions.assertEquals(2, projects.size());
    }

    @Order(7)
    @Test
    void cleanUp() {
        mUserRepository.deleteByEmail("marek@seget@wp.pl");
        mUserRepository.deleteByEmail("tomasz@seget@wp.pl");
        mUserRepository.deleteByEmail("kamil@seget@wp.pl");
    }
}
