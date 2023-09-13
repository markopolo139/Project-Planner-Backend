package pl.ms.projectoverview.app.entitites;

import pl.ms.projectoverview.app.persistence.entities.UserEntity;

import java.util.Objects;
import java.util.Set;

public class ProjectPlan {
    private Integer projectPlanId;

    private String title;

    private String language;

    private Set<String> features;

    private Set<String> goals;

    private Set<String> points;

    private User user;

    public ProjectPlan(
            Integer projectPlanId, String title, String language, Set<String> features, Set<String> goals,
            Set<String> points, User user
    ) {
        this.projectPlanId = projectPlanId;
        this.title = title;
        this.language = language;
        this.features = features;
        this.goals = goals;
        this.points = points;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                && Objects.equals(getPoints(), that.getPoints())
                && Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectPlanId(), getTitle(), getLanguage(), getFeatures(), getGoals(), getPoints(), getUser());
    }
}
