package pl.ms.projectoverview.web.models;

import jakarta.validation.constraints.*;
import pl.ms.projectoverview.app.entitites.ProjectStatus;
import pl.ms.projectoverview.web.validators.ProjectStatusValidator;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

public class ProjectModel {

    @NotNull
    @Min(0)
    private final Integer projectId;

    @NotBlank
    private final String githubLink;

    @NotBlank
    private final String title;

    private final String description;

    @NotBlank
    private final String language;

    @Future
    private final LocalDateTime deadline;

    @NotNull
    private final LocalDateTime dateOfStart;

    @NotNull
    private final Boolean isCurrent;

    @ProjectStatusValidator
    private final String projectStatus;

    @NotNull
    private final Set<String> features;

    @NotNull
    private final Set<String> goals;

    @NotNull
    private final Set<String> technologies;

    public ProjectModel(
            Integer projectId, String githubLink, String title, String description, String language,
            LocalDateTime deadline, LocalDateTime dateOfStart, Boolean isCurrent, String projectStatus,
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

    public LocalDateTime getDateOfStart() {
        return dateOfStart;
    }

    public Boolean getCurrent() {
        return isCurrent;
    }

    public String getProjectStatus() {
        return projectStatus;
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
}
