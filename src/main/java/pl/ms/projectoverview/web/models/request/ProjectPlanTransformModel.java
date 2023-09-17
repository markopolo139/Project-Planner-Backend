package pl.ms.projectoverview.web.models.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;

public class ProjectPlanTransformModel {

    @NotNull
    @Min(0)
    private final Integer planId;

    @NotBlank
    private final String githubLink;

    private final String description;

    @Future
    private final LocalDateTime deadline;

    @FutureOrPresent
    private final LocalDateTime startDate;

    @NotNull
    private final Set<String> technologies;

    public ProjectPlanTransformModel(
            Integer planId, String githubLink, String description, LocalDateTime deadline, LocalDateTime startDate,
            Set<String> technologies
    ) {
        this.planId = planId;
        this.githubLink = githubLink;
        this.description = description;
        this.deadline = deadline;
        this.startDate = startDate;
        this.technologies = technologies;
    }

    public Integer getPlanId() {
        return planId;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Set<String> getTechnologies() {
        return technologies;
    }
}
