package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.AssessmentProject;
import com.zmdev.goldenbag.domain.AssessmentProjectItem;
import com.zmdev.goldenbag.domain.AssessmentTemplate;
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
    public Result index(@RequestParam(defaultValue = "") String type) {
        if ("".equals(type)) {
            return ResultGenerator.genSuccessResult(assessmentTemplateService.findAll());
        }
        return ResultGenerator.genSuccessResult(
                assessmentTemplateService.findByType(
                        "staff".equals(type) ? AssessmentTemplate.Type.STAFF_TEMPLATE :
                                AssessmentTemplate.Type.MANAGER_TEMPLATE
                )
        );
    }

    @GetMapping("/{templateId}")
    public Result show(@PathVariable Long templateId) {
        return ResultGenerator.genSuccessResult(assessmentTemplateService.findById(templateId));
    }

    @PostMapping
    public Result store(@RequestBody AssessmentTemplate template) {
        template.setId(null);
        return ResultGenerator.genSuccessResult(assessmentTemplateService.save(template));
    }

    @RequestMapping(value = "/{templateId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result update(@PathVariable Long templateId, @RequestBody AssessmentTemplate template) {
        return ResultGenerator.genSuccessResult(assessmentTemplateService.updateTemplate(templateId, template));
    }

    @PostMapping("/{templateId}/project")
    public Result storeProject(@PathVariable Long templateId, @RequestBody AssessmentProject assessmentProject) {
        assessmentProject.setId(null);
        return ResultGenerator.genSuccessResult(assessmentTemplateService.saveProject(templateId, assessmentProject));
    }

    @RequestMapping(value = "/project/{projectId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result updateProject(@PathVariable Long projectId, @RequestBody AssessmentProject assessmentProject) {
        return ResultGenerator.genSuccessResult(assessmentTemplateService.updateProject(projectId, assessmentProject));
    }

    @PostMapping("/{projectId}/project_item")
    public Result storeProjectItem(@PathVariable Long projectId, @RequestBody AssessmentProjectItem projectItem) {
        projectItem.setId(null);
        return ResultGenerator.genSuccessResult(assessmentTemplateService.saveProjectItem(projectId, projectItem));
    }

    @RequestMapping(value = "/project_item/{projectItemId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result updateProjectItem(@PathVariable Long projectItemId, @RequestBody AssessmentProjectItem projectItem) {
        return ResultGenerator.genSuccessResult(assessmentTemplateService.updateProjectItem(projectItemId, projectItem));
    }

}
