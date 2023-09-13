package pl.ms.projectoverview.app.persistence.converters;

import org.springframework.stereotype.Component;
import pl.ms.projectoverview.app.entitites.ProjectPlan;
import pl.ms.projectoverview.app.persistence.entities.ProjectPlanEntity;

@Component
public class ProjectPlanConverter implements Converter<ProjectPlan, ProjectPlanEntity> {
    @Override
    public ProjectPlan convertToApp(ProjectPlanEntity projectPlan) {
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
}
