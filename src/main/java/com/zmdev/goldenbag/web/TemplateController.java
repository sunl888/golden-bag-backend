package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.AssessmentProject;
import com.zmdev.goldenbag.domain.AssessmentProjectItem;
import com.zmdev.goldenbag.service.AssessmentTemplateService;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/templates")
public class TemplateController extends BaseController {

    private AssessmentTemplateService assessmentTemplateService;

    public TemplateController(@Autowired AssessmentTemplateService assessmentTemplateService) {
        this.assessmentTemplateService = assessmentTemplateService;
    }

    @GetMapping
    public Result index() {
        return ResultGenerator.genSuccessResult(assessmentTemplateService.findAll());
    }


    @PostMapping("/{templateId}/project")
    public Result storeProject(@PathVariable Long templateId, @RequestBody AssessmentProject assessmentProject) {
        assessmentProject.setId(null);
        assessmentTemplateService.saveProject(templateId, assessmentProject);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(value = "/project/{projectId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result updateProject(@PathVariable Long projectId, @RequestBody AssessmentProject assessmentProject) {
        assessmentTemplateService.updateProject(projectId, assessmentProject);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/{projectId}/project_item")
    public Result storeProjectItem(@PathVariable Long projectId, @RequestBody AssessmentProjectItem projectItem) {
        projectItem.setId(null);
        assessmentTemplateService.saveProjectItem(projectId, projectItem);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(value = "/project_item/{projectItemId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result updateProjectItem(@PathVariable Long projectItemId, @RequestBody AssessmentProjectItem projectItem) {
        assessmentTemplateService.updateProjectItem(projectItemId, projectItem);
        return ResultGenerator.genSuccessResult();
    }

}
