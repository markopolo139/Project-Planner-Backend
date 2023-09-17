package pl.ms.projectoverview.app.converters;

import org.springframework.stereotype.Component;
import pl.ms.projectoverview.app.entitites.ProjectPlan;
import pl.ms.projectoverview.app.persistence.entities.ProjectPlanEntity;
import pl.ms.projectoverview.web.models.ProjectPlanModel;

import java.util.List;

public class ProjectPlanConverter{
    public static ProjectPlan convertEntityToApp(ProjectPlanEntity projectPlan) {
        return new ProjectPlan(
                projectPlan.getProjectPlanId(), projectPlan.getTitle(), projectPlan.getLanguage(),
                projectPlan.getFeatures(), projectPlan.getGoals(), projectPlan.getPoints()
        );
    }

    public static ProjectPlanEntity convertToEntity(ProjectPlan appEntity) {
        return new ProjectPlanEntity(
                appEntity.getProjectPlanId(), appEntity.getTitle(), appEntity.getLanguage(),
                appEntity.getFeatures(), appEntity.getGoals(), appEntity.getPoints(), null
        );
    }

    public static ProjectPlan convertModelToApp(ProjectPlanModel model) {
        return new ProjectPlan(
                model.getProjectPlanId(), model.getTitle(), model.getLanguage(), model.getFeatures(), model.getGoals(),
                model.getPoints()
        );
    }

    public static ProjectPlanModel convertToModel(ProjectPlan appEntity) {
        return new ProjectPlanModel(
                appEntity.getProjectPlanId(), appEntity.getTitle(), appEntity.getLanguage(), appEntity.getFeatures(),
                appEntity.getGoals(), appEntity.getPoints()
        );

    }

    public static List<ProjectPlan> convertEntityToApp(List<ProjectPlanEntity> entities) {
        return entities.stream().map(ProjectPlanConverter::convertEntityToApp).toList();
    }

    public static List<ProjectPlanEntity> convertToEntity(List<ProjectPlan> appEntities) {
        return appEntities.stream().map(ProjectPlanConverter::convertToEntity).toList();
    }
    public static List<ProjectPlan> convertModelToApp(List<ProjectPlanModel> models) {
        return models.stream().map(ProjectPlanConverter::convertModelToApp).toList();
    }

    public static List<ProjectPlanModel> convertToModel(List<ProjectPlan> appEntities) {
        return appEntities.stream().map(ProjectPlanConverter::convertToModel).toList();
    }
}
