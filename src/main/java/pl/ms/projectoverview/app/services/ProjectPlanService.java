package pl.ms.projectoverview.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.ms.projectoverview.app.entitites.ProjectPlan;
import pl.ms.projectoverview.app.entitites.ProjectStatus;
import pl.ms.projectoverview.app.exceptions.NotCurrentUserProjectPlanException;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.persistence.converters.ProjectConverter;
import pl.ms.projectoverview.app.persistence.converters.ProjectPlanConverter;
import pl.ms.projectoverview.app.persistence.entities.ProjectEntity;
import pl.ms.projectoverview.app.persistence.entities.ProjectPlanEntity;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;
import pl.ms.projectoverview.app.persistence.repositories.ProjectPlanRepository;
import pl.ms.projectoverview.app.persistence.repositories.ProjectRepository;
import pl.ms.projectoverview.app.persistence.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class ProjectPlanService {

    private final Logger mLogger = LogManager.getLogger();

    private final ProjectPlanRepository mProjectPlanRepository;

    private final ProjectPlanConverter mProjectPlanConverter;

    private final UserRepository mUserRepository;

    private final Integer userId = AppUtils.getUserId();

    public ProjectPlanService(
            ProjectPlanRepository projectPlanRepository, ProjectPlanConverter projectPlanConverter, UserRepository userRepository
    ) {
        mProjectPlanRepository = projectPlanRepository;
        mProjectPlanConverter = projectPlanConverter;
        mUserRepository = userRepository;
    }

    public void createPlan(ProjectPlan projectPlan) throws UserNotFoundException {
        UserEntity loggedInUser = AppUtils.getCurrentUser(mUserRepository);
        ProjectPlanEntity newPlan = mProjectPlanConverter.convertToEntity(projectPlan);
        loggedInUser.addProjectPlan(newPlan);

        mUserRepository.save(loggedInUser);
    }

    public void updatePlan(ProjectPlan projectPlan) throws NotCurrentUserProjectPlanException, UserNotFoundException {
        ProjectPlanEntity updateProjectPlan = mProjectPlanConverter.convertToEntity(projectPlan);
        if (!mProjectPlanRepository.existsByProjectPlanIdAndUser_UserId(updateProjectPlan.getProjectPlanId(), userId)) {
            mLogger.error("Selected plan does not belong to currently logged in user");
            throw new NotCurrentUserProjectPlanException();
        }
        updateProjectPlan.setUser(AppUtils.getCurrentUser(mUserRepository));

        mProjectPlanRepository.save(updateProjectPlan);

    }

    public void deletePlan(Integer projectPlanId) throws NotCurrentUserProjectPlanException {
        if (!mProjectPlanRepository.existsByProjectPlanIdAndUser_UserId(projectPlanId, userId)) {
            mLogger.error("Selected plan does not belong to currently logged in user");
            throw new NotCurrentUserProjectPlanException();
        }

        mProjectPlanRepository.deleteById(projectPlanId);
    }

    public List<ProjectPlan> filterQuery(String language) {
        return mProjectPlanConverter.convertToApp(mProjectPlanRepository.filterQuery(userId, language));
    }

    public List<ProjectPlan> getUserProjectPlans() {
        return mProjectPlanConverter.convertToApp(mProjectPlanRepository.findAllByUser_UserId(userId));
    }

    public void transformProjectToEntity(
            Integer planId, String githubLink, String description, LocalDateTime deadline, LocalDateTime startDate,
            Set<String> technologies
    ) throws NotCurrentUserProjectPlanException, UserNotFoundException {
        if (!mProjectPlanRepository.existsByProjectPlanIdAndUser_UserId(planId, userId)) {
            mLogger.error("Selected plan does not belong to currently logged in user");
            throw new NotCurrentUserProjectPlanException();
        }
        ProjectPlanEntity plan = mProjectPlanRepository.findById(planId).orElseThrow();

        ProjectEntity transformedProject = new ProjectEntity(
                0, githubLink, plan.getTitle(), description, plan.getLanguage(), deadline, startDate, false,
                ProjectStatus.NOT_STARTED, plan.getFeatures(), plan.getGoals(), technologies, null
        );

        UserEntity loggedInUser = AppUtils.getCurrentUser(mUserRepository);
        loggedInUser.addProject(transformedProject);

        mUserRepository.save(loggedInUser);
    }
}
