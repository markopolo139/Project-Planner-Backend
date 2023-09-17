package pl.ms.projectoverview.app.converters;

import org.springframework.stereotype.Component;
import pl.ms.projectoverview.app.entitites.ProjectPlan;
import pl.ms.projectoverview.app.persistence.entities.ProjectPlanEntity;
import pl.ms.projectoverview.web.models.ProjectPlanModel;

@Component
public class ProjectPlanConverter implements Converter<ProjectPlan, ProjectPlanEntity, ProjectPlanModel> {
    @Override
    public ProjectPlan convertEntityToApp(ProjectPlanEntity projectPlan) {
        return new ProjectPlan(
                projectPlan.getProjectPlanId(), projectPlan.getTitle(), projectPlan.getLanguage(),
                projectPlan.getFeatures(), projectPlan.getGoals(), projectPlan.getPoints()
        );
    }

    @Override
    public ProjectPlanEntity convertToEntity(ProjectPlan appEntity) {
        return new ProjectPlanEntity(
                appEntity.getProjectPlanId(), appEntity.getTitle(), appEntity.getLanguage(),
                appEntity.getFeatures(), appEntity.getGoals(), appEntity.getPoints(), null
        );
    }

    @Override
    public ProjectPlan convertModelToApp(ProjectPlanModel entity) {
        return null;
    }

    @Override
    public ProjectPlanModel convertToModel(ProjectPlan appEntity) {
        return null;
    }
}
