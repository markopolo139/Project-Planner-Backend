package pl.ms.projectoverview.app.persistence.entities;

import jakarta.persistence.*;
import pl.ms.projectoverview.app.entitites.ProjectStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "project_plans")
public class ProjectPlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_plan_id")
    private Integer projectPlanId;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "language", nullable = false, length = 127)
    private String language;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_plans_features", joinColumns = @JoinColumn(name = "project_plan_id"))
    @Column(name = "feature")
    private Set<String> features;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_plans_goals", joinColumns = @JoinColumn(name = "project_plan_id"))
    @Column(name = "goal")
    private Set<String> goals;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_plans_points", joinColumns = @JoinColumn(name = "project_plan_id"))
    @Column(name = "points")
    private Set<String> points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity user;

    public ProjectPlanEntity(
            Integer projectPlanId, String title, String language, Set<String> features, Set<String> goals,
            Set<String> points, UserEntity user
    ) {
        this.projectPlanId = projectPlanId;
        this.title = title;
        this.language = language;
        this.features = features;
        this.goals = goals;
        this.points = points;
        this.user = user;
    }

    protected ProjectPlanEntity() { }

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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectPlanEntity that = (ProjectPlanEntity) o;
        return Objects.equals(getProjectPlanId(), that.getProjectPlanId()) && Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectPlanId(), getTitle());
    }
}
