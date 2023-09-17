package pl.ms.projectoverview.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.ms.projectoverview.app.entitites.Project;
import pl.ms.projectoverview.app.entitites.ProjectStatus;
import pl.ms.projectoverview.app.exceptions.NotCurrentUserProjectException;
import pl.ms.projectoverview.app.exceptions.TitleNotFoundException;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.converters.ProjectConverter;
import pl.ms.projectoverview.app.persistence.entities.ProjectEntity;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;
import pl.ms.projectoverview.app.persistence.repositories.ProjectRepository;
import pl.ms.projectoverview.app.persistence.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static pl.ms.projectoverview.app.converters.ProjectConverter.convertEntityToApp;
import static pl.ms.projectoverview.app.converters.ProjectConverter.convertToEntity;

@Service
public class ProjectService {

    private final Logger mLogger = LogManager.getLogger();

    private final ProjectRepository mProjectRepository;

    private final UserRepository mUserRepository;

    public ProjectService(ProjectRepository mProjectRepository, UserRepository userRepository) {
        this.mProjectRepository = mProjectRepository;
        this.mUserRepository = userRepository;
    }

    public void createProject(Project project) throws UserNotFoundException {
        ProjectEntity newProject = convertToEntity(project);
        UserEntity loggedInUser = AppUtils.getCurrentUser(mUserRepository);
        loggedInUser.addProject(newProject);

        mUserRepository.save(loggedInUser);
    }

    public void createProjects(List<Project> projects) throws UserNotFoundException {
        UserEntity loggedInUser = AppUtils.getCurrentUser(mUserRepository);
        loggedInUser.getProjects().addAll(
                convertToEntity(projects,loggedInUser)
        );

        mUserRepository.save(loggedInUser);
    }

    public void updateProject(Project project) throws UserNotFoundException, NotCurrentUserProjectException {
        ProjectEntity updateProject = convertToEntity(project);
        if (!mProjectRepository.existsByProjectIdAndUser_UserId(updateProject.getProjectId(), AppUtils.getUserId())) {
            mLogger.error("Selected project does not belong to logged in user");
            throw new NotCurrentUserProjectException();
        }
        updateProject.setUser(AppUtils.getCurrentUser(mUserRepository));

        mProjectRepository.save(updateProject);
    }

    public List<Project> filterQuery(
            String language, LocalDateTime dateOfStartBeginning, LocalDateTime dateOfStartEnding,
            Boolean isCurrentProject, ProjectStatus projectStatus
    ) {
        return convertEntityToApp(
                mProjectRepository.filterQuery(
                        AppUtils.getUserId(), language, dateOfStartBeginning, dateOfStartEnding, isCurrentProject,
                        projectStatus
                )
        );
    }

    public List<Project> getUserProjects() {
        return convertEntityToApp(mProjectRepository.findAllByUser_UserId(AppUtils.getUserId()));
    }

    public Project getByTitle(String title) throws TitleNotFoundException, NotCurrentUserProjectException {
        Project project = convertEntityToApp(
                mProjectRepository.findByTitle(title).orElseThrow(TitleNotFoundException::new)
        );
        if (!mProjectRepository.existsByProjectIdAndUser_UserId(project.getProjectId(), AppUtils.getUserId())) {
            mLogger.error("Selected project does not belong to logged in user");
            throw new NotCurrentUserProjectException();
        }

        return project;
    }
}
