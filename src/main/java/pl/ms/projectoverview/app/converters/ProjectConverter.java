package pl.ms.projectoverview.app.converters;


import org.springframework.stereotype.Component;
import pl.ms.projectoverview.app.entitites.Project;
import pl.ms.projectoverview.app.persistence.entities.ProjectEntity;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;
import pl.ms.projectoverview.web.models.ProjectModel;

import java.util.List;

@Component
public class ProjectConverter implements Converter<Project, ProjectEntity, ProjectModel> {
    @Override
    public Project convertEntityToApp(ProjectEntity projectEntity) {
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

    @Override
    public Project convertModelToApp(ProjectModel entity) {
        return null;
    }

    @Override
    public ProjectModel convertToModel(Project appEntity) {
        return null;
    }
}
