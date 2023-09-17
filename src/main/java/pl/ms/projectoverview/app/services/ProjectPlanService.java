package pl.ms.projectoverview.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.ms.projectoverview.app.entitites.ProjectPlan;
import pl.ms.projectoverview.app.entitites.ProjectStatus;
import pl.ms.projectoverview.app.exceptions.NotCurrentUserProjectException;
import pl.ms.projectoverview.app.exceptions.NotCurrentUserProjectPlanException;
import pl.ms.projectoverview.app.exceptions.TitleNotFoundException;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.converters.ProjectPlanConverter;
import pl.ms.projectoverview.app.persistence.entities.ProjectEntity;
import pl.ms.projectoverview.app.persistence.entities.ProjectPlanEntity;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;
import pl.ms.projectoverview.app.persistence.repositories.ProjectPlanRepository;
import pl.ms.projectoverview.app.persistence.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static pl.ms.projectoverview.app.converters.ProjectPlanConverter.convertEntityToApp;
import static pl.ms.projectoverview.app.converters.ProjectPlanConverter.convertToEntity;

@Service
public class ProjectPlanService {

    private final Logger mLogger = LogManager.getLogger();

    private final ProjectPlanRepository mProjectPlanRepository;

    private final UserRepository mUserRepository;

    public ProjectPlanService(
            ProjectPlanRepository projectPlanRepository, UserRepository userRepository
    ) {
        mProjectPlanRepository = projectPlanRepository;
        mUserRepository = userRepository;
    }

    public void createPlan(ProjectPlan projectPlan) throws UserNotFoundException {
        UserEntity loggedInUser = AppUtils.getCurrentUser(mUserRepository);
        ProjectPlanEntity newPlan = convertToEntity(projectPlan);
        loggedInUser.addProjectPlan(newPlan);

        mUserRepository.save(loggedInUser);
    }

    public void updatePlan(ProjectPlan projectPlan) throws NotCurrentUserProjectPlanException, UserNotFoundException {
        ProjectPlanEntity updateProjectPlan = convertToEntity(projectPlan);
        if (!mProjectPlanRepository.existsByProjectPlanIdAndUser_UserId(updateProjectPlan.getProjectPlanId(), AppUtils.getUserId())) {
            mLogger.error("Selected plan does not belong to currently logged in user");
            throw new NotCurrentUserProjectPlanException();
        }
        updateProjectPlan.setUser(AppUtils.getCurrentUser(mUserRepository));

        mProjectPlanRepository.save(updateProjectPlan);

    }

    public void deletePlan(Integer projectPlanId) throws NotCurrentUserProjectPlanException {
        if (!mProjectPlanRepository.existsByProjectPlanIdAndUser_UserId(projectPlanId, AppUtils.getUserId())) {
            mLogger.error("Selected plan does not belong to currently logged in user");
            throw new NotCurrentUserProjectPlanException();
        }

        mProjectPlanRepository.deleteById(projectPlanId);
    }

    public List<ProjectPlan> filterQuery(String language) {
        return convertEntityToApp(mProjectPlanRepository.filterQuery(AppUtils.getUserId(), language));
    }

    public List<ProjectPlan> getUserProjectPlans() {
        return convertEntityToApp(mProjectPlanRepository.findAllByUser_UserId(AppUtils.getUserId()));
    }

    public ProjectPlan getByTitle(String title) throws TitleNotFoundException, NotCurrentUserProjectException {
        ProjectPlan plan = convertEntityToApp(
                mProjectPlanRepository.findByTitle(title).orElseThrow(TitleNotFoundException::new)
        );
        if (!mProjectPlanRepository.existsByProjectPlanIdAndUser_UserId(plan.getProjectPlanId(), AppUtils.getUserId())) {
            mLogger.error("Selected project does not belong to logged in user");
            throw new NotCurrentUserProjectException();
        }

        return plan;
    }

    public void transformProjectToEntity(
            Integer planId, String githubLink, String description, LocalDateTime deadline, LocalDateTime startDate,
            Set<String> technologies
    ) throws NotCurrentUserProjectPlanException, UserNotFoundException {
        if (!mProjectPlanRepository.existsByProjectPlanIdAndUser_UserId(planId, AppUtils.getUserId())) {
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
