package pl.ms.projectoverview.web.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.ms.projectoverview.app.converters.ProjectPlanConverter;
import pl.ms.projectoverview.app.entitites.ProjectPlan;
import pl.ms.projectoverview.app.exceptions.NotCurrentUserProjectException;
import pl.ms.projectoverview.app.exceptions.NotCurrentUserProjectPlanException;
import pl.ms.projectoverview.app.exceptions.TitleNotFoundException;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import pl.ms.projectoverview.app.services.ProjectPlanService;
import pl.ms.projectoverview.web.models.ProjectPlanModel;
import pl.ms.projectoverview.web.models.request.ProjectPlanTransformModel;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static pl.ms.projectoverview.app.converters.ProjectPlanConverter.convertModelToApp;
import static pl.ms.projectoverview.app.converters.ProjectPlanConverter.convertToModel;

@RestController
@CrossOrigin
@Validated
public class ProjectPlanController {

    private final ProjectPlanService mProjectPlanService;

    public ProjectPlanController(ProjectPlanService projectPlanService) {
        mProjectPlanService = projectPlanService;
    }

    @PostMapping("/api/v1/project/plan/create")
    public void createPlan(@RequestBody @Valid ProjectPlanModel planModel) throws UserNotFoundException {
        mProjectPlanService.createPlan(convertModelToApp(planModel));
    }

    @PutMapping("/api/v1/project/plan/update")
    public void updatePlan(@RequestBody @Valid ProjectPlanModel planModel)
            throws UserNotFoundException, NotCurrentUserProjectPlanException {
        mProjectPlanService.updatePlan(convertModelToApp(planModel));
    }

    @DeleteMapping("/api/v1/project/plan/delete")
    public void deletePlan(@RequestParam("id") @Valid @Min(0) Integer projectPlanId) throws NotCurrentUserProjectPlanException {
        mProjectPlanService.deletePlan(projectPlanId);
    }

    @GetMapping("/api/v1/project/plan/filter")
    public List<ProjectPlanModel> filterQuery(
            @RequestParam(name = "language", required = false) @Valid @NotBlank String language
    ) {
        return convertToModel(mProjectPlanService.filterQuery(language));
    }

    @GetMapping("/api/v1/project/plan/get")
    public List<ProjectPlanModel> getUserProjectPlans() {
        return convertToModel(mProjectPlanService.getUserProjectPlans());
    }

    @GetMapping("/api/v1/project/plan/get/title")
    public ProjectPlanModel getByTitle(@RequestParam("title") @Valid @NotBlank String title)
            throws TitleNotFoundException, NotCurrentUserProjectException {
        return convertToModel(mProjectPlanService.getByTitle(title));
    }

    @PostMapping("/api/v1/project/plan/transform")
    public void transformProjectToEntity(@RequestBody @Valid ProjectPlanTransformModel model)
            throws UserNotFoundException, NotCurrentUserProjectPlanException {
        mProjectPlanService.transformProjectToEntity(
                model.getPlanId(), model.getGithubLink(), model.getDescription(), model.getDeadline(),
                model.getStartDate(), model.getTechnologies()
        );
    }
}
