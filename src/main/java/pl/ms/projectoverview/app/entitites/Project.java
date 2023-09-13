package pl.ms.projectoverview.app.entitites;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class Project {

    private final Integer projectId;

    private String githubLink;

    private String title;

    private String description;

    private String language;

    private LocalDateTime deadline;

    private final LocalDateTime dateOfStart;

    private Boolean isCurrent;

    private ProjectStatus projectStatus;

    private final Set<String> features;

    private final Set<String> goals;

    private final Set<String> technologies;

    public Project(
            Integer projectId, String githubLink, String title, String description, String language,
            LocalDateTime deadline, LocalDateTime dateOfStart, Boolean isCurrent, ProjectStatus projectStatus,
            Set<String> features, Set<String> goals, Set<String> technologies
    ) {
        this.projectId = projectId;
        this.githubLink = githubLink;
        this.title = title;
        this.description = description;
        this.language = language;
        this.deadline = deadline;
        this.dateOfStart = dateOfStart;
        this.isCurrent = isCurrent;
        this.projectStatus = projectStatus;
        this.features = features;
        this.goals = goals;
        this.technologies = technologies;
    }

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

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(Boolean current) {
        isCurrent = current;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(getProjectId(), project.getProjectId())
                && Objects.equals(getGithubLink(), project.getGithubLink())
                && Objects.equals(getTitle(), project.getTitle())
                && Objects.equals(getDescription(), project.getDescription())
                && Objects.equals(getLanguage(), project.getLanguage())
                && Objects.equals(getDeadline(), project.getDeadline())
                && Objects.equals(getDateOfStart(), project.getDateOfStart())
                && Objects.equals(isCurrent, project.isCurrent)
                && getProjectStatus() == project.getProjectStatus()
                && Objects.equals(getFeatures(), project.getFeatures())
                && Objects.equals(getGoals(), project.getGoals())
                && Objects.equals(getTechnologies(), project.getTechnologies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getProjectId(), getGithubLink(), getTitle(), getDescription(), getLanguage(), getDeadline(),
                getDateOfStart(), isCurrent, getProjectStatus(), getFeatures(), getGoals(), getTechnologies()
        );
    }
}
