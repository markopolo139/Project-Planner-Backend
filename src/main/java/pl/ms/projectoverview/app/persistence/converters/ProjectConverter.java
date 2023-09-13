package pl.ms.projectoverview.app.persistence.converters;


import org.springframework.stereotype.Component;
import pl.ms.projectoverview.app.entitites.Project;
import pl.ms.projectoverview.app.persistence.entities.ProjectEntity;

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
}
