package pl.ms.projectoverview.app.converters;


import pl.ms.projectoverview.app.entitites.Project;
import pl.ms.projectoverview.app.persistence.entities.ProjectEntity;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;
import pl.ms.projectoverview.web.models.ProjectModel;
import pl.ms.projectoverview.web.models.ProjectPlanModel;

import java.util.List;

public class ProjectConverter {
    public static Project convertEntityToApp(ProjectEntity projectEntity) {
        return new Project(
                projectEntity.getProjectId(), projectEntity.getGithubLink(), projectEntity.getTitle(),
                projectEntity.getDescription(), projectEntity.getLanguage(), projectEntity.getDeadline(),
                projectEntity.getDateOfStart(), projectEntity.isCurrentProject(), projectEntity.getProjectStatus(),
                projectEntity.getFeatures(), projectEntity.getGoals(), projectEntity.getTechnologies()
        );
    }

    public static ProjectEntity convertToEntity(Project appEntity) {
        return new ProjectEntity(
                appEntity.getProjectId(), appEntity.getGithubLink(), appEntity.getTitle(),
                appEntity.getDescription(), appEntity.getLanguage(), appEntity.getDeadline(),
                appEntity.getDateOfStart(), appEntity.isCurrent(), appEntity.getProjectStatus(),
                appEntity.getFeatures(), appEntity.getGoals(), appEntity.getTechnologies(), null
        );
    }

    private static ProjectEntity convertToEntity(Project appEntity, UserEntity userEntity) {
        return new ProjectEntity(
                appEntity.getProjectId(), appEntity.getGithubLink(), appEntity.getTitle(),
                appEntity.getDescription(), appEntity.getLanguage(), appEntity.getDeadline(),
                appEntity.getDateOfStart(), appEntity.isCurrent(), appEntity.getProjectStatus(),
                appEntity.getFeatures(), appEntity.getGoals(), appEntity.getTechnologies(), userEntity
        );
    }

    public static List<ProjectEntity> convertToEntity(List<Project> projects, UserEntity userEntity) {
        return projects.stream().map(it -> convertToEntity(it, userEntity)).toList();
    }

    public static Project convertModelToApp(ProjectModel model) {
        return new Project(
                model.getProjectId(), model.getGithubLink(), model.getTitle(), model.getDescription(), model.getLanguage(),
                model.getDeadline(), model.getDateOfStart(), model.getCurrent(), model.getProjectStatus(),
                model.getFeatures(), model.getGoals(), model.getTechnologies()
        );

    }

    public static ProjectModel convertToModel(Project appEntity) {
        return new ProjectModel(
                appEntity.getProjectId(), appEntity.getGithubLink(), appEntity.getTitle(),
                appEntity.getDescription(), appEntity.getLanguage(), appEntity.getDeadline(),
                appEntity.getDateOfStart(), appEntity.isCurrent(), appEntity.getProjectStatus(),
                appEntity.getFeatures(), appEntity.getGoals(), appEntity.getTechnologies()
        );

    }

    public static List<Project> convertEntityToApp(List<ProjectEntity> entities) {
        return entities.stream().map(ProjectConverter::convertEntityToApp).toList();
    }

    public static List<ProjectEntity> convertToEntity(List<Project> appEntities) {
        return appEntities.stream().map(ProjectConverter::convertToEntity).toList();
    }
    public static List<Project> convertModelToApp(List<ProjectModel> models) {
        return models.stream().map(ProjectConverter::convertModelToApp).toList();
    }

    public static List<ProjectModel> convertToModel(List<Project> appEntities) {
        return appEntities.stream().map(ProjectConverter::convertToModel).toList();
    }
}
