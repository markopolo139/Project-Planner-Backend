package pl.ms.projectoverview.web.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.ms.projectoverview.app.converters.ProjectConverter;
import pl.ms.projectoverview.app.entitites.Project;
import pl.ms.projectoverview.app.entitites.ProjectStatus;
import pl.ms.projectoverview.app.exceptions.NotCurrentUserProjectException;
import pl.ms.projectoverview.app.exceptions.TitleNotFoundException;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.services.ProjectService;
import pl.ms.projectoverview.web.models.ProjectModel;

import java.time.LocalDateTime;
import java.util.List;

import static pl.ms.projectoverview.app.converters.ProjectConverter.convertModelToApp;
import static pl.ms.projectoverview.app.converters.ProjectConverter.convertToModel;

@RestController
@CrossOrigin
@Validated
public class ProjectController {

    private final ProjectService mProjectService;

    public ProjectController(ProjectService projectService) {
        mProjectService = projectService;
    }

    @PostMapping("/api/v1/project/create")
    public void createProject(@RequestBody @Valid ProjectModel projectModel) throws UserNotFoundException {
        mProjectService.createProject(convertModelToApp(projectModel));
    }

    @PutMapping("/api/v1/project/create/list")
    public void createProjects(@RequestBody List<@Valid ProjectModel> projectModel) throws UserNotFoundException {
        mProjectService.createProjects(convertModelToApp(projectModel));
    }

    @DeleteMapping("/api/v1/project/update")
    public void updateProject(@RequestBody @Valid ProjectModel projectModel)
            throws UserNotFoundException, NotCurrentUserProjectException {
        mProjectService.updateProject(convertModelToApp(projectModel));
    }

    @GetMapping("/api/v1/project/filter")
    public List<ProjectModel> filterQuery(
            @Param("language") String language,
            @Param("dateOfStartBeginning") LocalDateTime dateOfStartBeginning,
            @Param("dateOfStartEnding") LocalDateTime dateOfStartEnding,
            @Param("isCurrentProject") Boolean isCurrentProject,
            @Param("projectStatus") ProjectStatus projectStatus
    ) {
        return convertToModel(mProjectService.filterQuery(
                language,dateOfStartBeginning,dateOfStartEnding,isCurrentProject, projectStatus
        ));
    }

    @GetMapping("/api/v1/project/get")
    public List<ProjectModel> getUserProjects() {
        return convertToModel(mProjectService.getUserProjects());
    }

    @GetMapping("/api/v1/project/get/title")
    public ProjectModel getByTitle(@RequestParam("title") @Valid @NotBlank String title)
            throws TitleNotFoundException, NotCurrentUserProjectException {
        return convertToModel(mProjectService.getByTitle(title));
    }
}
