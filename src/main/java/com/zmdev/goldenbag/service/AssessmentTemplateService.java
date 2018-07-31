package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.*;

import java.util.List;
import java.util.Map;

public interface AssessmentTemplateService extends BaseService<AssessmentTemplate, Long> {

    AssessmentTemplate saveTemplate(AssessmentTemplate template);

    AssessmentTemplate findByTypeAndQuarter(AssessmentTemplate.Type type, Quarter quarter);

    List<AssessmentTemplate> findByQuarter(Quarter quarter);


    AssessmentTemplate updateTemplate(Long id, AssessmentTemplate template);

    public Map<Integer, List<AssessmentTemplate>> findByType(AssessmentTemplate.Type type);

    AssessmentProject saveProject(Long templateId, AssessmentProject project);

    AssessmentProject updateProject(Long projectId, AssessmentProject project);

    // delete project
    void deleteProject(Long projectId);

    // delete project item
    void deleteProjectItem(Long projectItemId);

    // delete input
    void deleteTemplateInput(Long templateInputId);

    AssessmentProjectItem saveProjectItem(Long projectId, AssessmentProjectItem projectItem);

    AssessmentProjectItem updateProjectItem(Long projectItemId, AssessmentProjectItem projectItem);

    AssessmentInput saveTemplateInput(Long templateId, AssessmentInput assessmentInput);

    AssessmentInput updateTemplateInput(Long inputId, AssessmentInput assessmentInput);
}
