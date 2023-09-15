package pl.ms.projectoverview.app.persistence.converters;


import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import pl.ms.projectoverview.app.entitites.Project;
import pl.ms.projectoverview.app.persistence.entities.ProjectEntity;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;

import java.util.List;

@Component
public class ProjectConverter implements Converter<Project, ProjectEntity> {
    @Override
    public Project convertToApp(ProjectEntity projectEntity) {
        return new Project(
                projectEntity.getProjectId(), projectEntity.getGithubLink(), projectEntity.getTitle(),
                projectEntity.getDescription(), projectEntity.getLanguage(), projectEntity.getDeadline(),
                projectEntity.getDateOfStart(), projectEntity.isCurrentProject(), projectEntity.getProjectStatus(),
                projectEntity.getFeatures(), projectEntity.getGoals(), projectEntity.getTechnologies()
        );
    }

    @Override
    public ProjectEntity convertToEntity(Project appEntity) {
        return new ProjectEntity(
                appEntity.getProjectId(), appEntity.getGithubLink(), appEntity.getTitle(),
                appEntity.getDescription(), appEntity.getLanguage(), appEntity.getDeadline(),
                appEntity.getDateOfStart(), appEntity.isCurrent(), appEntity.getProjectStatus(),
                appEntity.getFeatures(), appEntity.getGoals(), appEntity.getTechnologies(), null
        );
    }

    private ProjectEntity convertToEntity(Project appEntity, UserEntity userEntity) {
        return new ProjectEntity(
                appEntity.getProjectId(), appEntity.getGithubLink(), appEntity.getTitle(),
                appEntity.getDescription(), appEntity.getLanguage(), appEntity.getDeadline(),
                appEntity.getDateOfStart(), appEntity.isCurrent(), appEntity.getProjectStatus(),
                appEntity.getFeatures(), appEntity.getGoals(), appEntity.getTechnologies(), userEntity
        );
    }

    public List<ProjectEntity> convertToEntity(List<Project> projects, UserEntity userEntity) {
        return projects.stream().map(it -> convertToEntity(it, userEntity)).toList();
    }
}
