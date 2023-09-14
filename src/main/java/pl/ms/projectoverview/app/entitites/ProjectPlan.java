package pl.ms.projectoverview.app.entitites;

import pl.ms.projectoverview.app.persistence.entities.UserEntity;

import java.util.Objects;
import java.util.Set;

public class ProjectPlan {
    private final Integer projectPlanId;

    private String title;

    private String language;

    private final Set<String> features;

    private final Set<String> goals;

    private final Set<String> points;

    public ProjectPlan(
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

    public Set<String> getFeatures() {
        return features;
    }

    public Set<String> getGoals() {
        return goals;
    }

    public Set<String> getPoints() {
        return points;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectPlan that = (ProjectPlan) o;
        return Objects.equals(getProjectPlanId(), that.getProjectPlanId())
                && Objects.equals(getTitle(), that.getTitle())
                && Objects.equals(getLanguage(), that.getLanguage())
                && Objects.equals(getFeatures(), that.getFeatures())
                && Objects.equals(getGoals(), that.getGoals())
                && Objects.equals(getPoints(), that.getPoints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectPlanId(), getTitle(), getLanguage(), getFeatures(), getGoals(), getPoints());
    }
}
