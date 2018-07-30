package com.zmdev.goldenbag.web.template_module;

import com.zmdev.goldenbag.domain.AssessmentInput;
import com.zmdev.goldenbag.domain.AssessmentProject;
import com.zmdev.goldenbag.domain.AssessmentProjectItem;
import com.zmdev.goldenbag.domain.AssessmentTemplate;
import com.zmdev.goldenbag.exception.ModelNotFoundException;
import com.zmdev.goldenbag.service.AssessmentTemplateService;
import com.zmdev.goldenbag.utils.TemplateXls;
import com.zmdev.goldenbag.web.BaseController;
import com.zmdev.goldenbag.web.insterceptor.PermissionInterceptor;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping(value = "/templates", produces = "application/json;charset=UTF-8")
public class TemplateController extends BaseController {

    public TemplateController() {
        PermissionInterceptor.addSpecialAbilitie(getClass(), "storeProject", "edit");
        PermissionInterceptor.addSpecialAbilitie(getClass(), "updateProject", "edit");
        PermissionInterceptor.addSpecialAbilitie(getClass(), "storeProjectItem", "edit");
        PermissionInterceptor.addSpecialAbilitie(getClass(), "updateProjectItem", "edit");
        PermissionInterceptor.addSpecialAbilitie(getClass(), "storeTemplateInput", "edit");
        PermissionInterceptor.addSpecialAbilitie(getClass(), "updateTemplateInput", "edit");
    }

    private AssessmentTemplateService assessmentTemplateService;
    private TemplateXls templateXls;

    public TemplateController(@Autowired AssessmentTemplateService assessmentTemplateService, @Autowired TemplateXls templateXls) {
        this();
        this.assessmentTemplateService = assessmentTemplateService;
        this.templateXls = templateXls;
    }

    @GetMapping
    public Result index(@RequestParam(defaultValue = "") String type) {
        if ("".equals(type)) {
            return ResultGenerator.genSuccessResult(assessmentTemplateService.findByType(null));
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

    @PostMapping("/{templateId}/template_input")
    public Result storeTemplateInput(@PathVariable Long templateId, @RequestBody AssessmentInput assessmentInput) {
        assessmentInput.setId(null);
        return ResultGenerator.genSuccessResult(assessmentTemplateService.saveTemplateInput(templateId, assessmentInput));
    }

    @RequestMapping(value = "/template_input/{templateInputId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result updateTemplateInput(@PathVariable Long templateInputId, @RequestBody AssessmentInput assessmentInput) {
        return ResultGenerator.genSuccessResult(assessmentTemplateService.updateTemplateInput(templateInputId, assessmentInput));
    }

    @GetMapping("{templateId}/export")
    public Result export(@PathVariable Long templateId) throws IOException {
        AssessmentTemplate template = assessmentTemplateService.findById(templateId).orElse(null);
        if (template == null) {
            throw new ModelNotFoundException("模版不存在");
        }
        Workbook workbook = templateXls.createTemplate(template);
        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        workbook.write(fileOut);
        fileOut.close();
        return ResultGenerator.genSuccessResult();
    }
}
