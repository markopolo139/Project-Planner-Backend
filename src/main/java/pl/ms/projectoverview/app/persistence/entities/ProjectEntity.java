package pl.ms.projectoverview.app.persistence.entities;

import jakarta.persistence.*;
import pl.ms.projectoverview.app.entitites.ProjectStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "github_link", unique = true, nullable = false)
    private String githubLink;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "language", nullable = false, length = 127)
    private String language;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "date_of_start", nullable = false)
    private LocalDateTime dateOfStart;

    @Column(name = "is_current_project", nullable = false)
    private Boolean isCurrentProject;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status", nullable = false)
    private ProjectStatus projectStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "projects_features", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "feature")
    private Set<String> features;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "projects_goals", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "goal")
    private Set<String> goals;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "projects_technologies", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "technology")
    private Set<String> technologies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public ProjectEntity(
            Integer projectId, String githubLink, String title, String description, String language,
            LocalDateTime deadline, LocalDateTime dateOfStart, Boolean isCurrentProject,
            ProjectStatus projectStatus, Set<String> features, Set<String> goals, Set<String> technologies,
            UserEntity user
    ) {
        this.projectId = projectId;
        this.githubLink = githubLink;
        this.title = title;
        this.description = description;
        this.language = language;
        this.deadline = deadline;
        this.dateOfStart = dateOfStart;
        this.isCurrentProject = isCurrentProject;
        this.projectStatus = projectStatus;
        this.features = features;
        this.goals = goals;
        this.technologies = technologies;
        this.user = user;
    }

    protected ProjectEntity() { }

    public Integer getProjectId() {
        return projectId;
    }

    public LocalDateTime getDateOfStart() {
        return dateOfStart;
    }

    public Set<String> getFeatures() {
        return features;
    }

    public Set<String> getGoals() {
        return goals;
    }

    public Set<String> getTechnologies() {
        return technologies;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public Boolean isCurrentProject() {
        return isCurrentProject;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
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
        ProjectEntity that = (ProjectEntity) o;
        return Objects.equals(getProjectId(), that.getProjectId())
                && Objects.equals(getGithubLink(), that.getGithubLink())
                && Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectId(), getGithubLink(), getTitle());
    }
}
