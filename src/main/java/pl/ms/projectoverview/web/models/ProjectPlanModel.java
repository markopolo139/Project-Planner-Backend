package pl.ms.projectoverview.web.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class ProjectPlanModel {

    @NotNull
    @Min(0)
    private final Integer projectPlanId;

    @NotBlank
    private final String title;

    @NotBlank
    private final String language;

    @NotNull
    private final Set<String> features;

    @NotNull
    private final Set<String> goals;

    @NotNull
    private final Set<String> points;

    public ProjectPlanModel(
            Integer projectPlanId, String title, String language, Set<String> features, Set<String> goals,
            Set<String> points
    ) {
        this.projectPlanId = projectPlanId;
        this.title = title;
        this.language = language;
        this.features = features;
        this.goals = goals;
        this.points = points;
    }

    public Integer getProjectPlanId() {
        return projectPlanId;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public Set<String> getFeatures() {
        return features;
    }

    public Set<String> getGoals() {
        return goals;
    }

    public Set<String> getPoints() {
        return points;
    }
}
