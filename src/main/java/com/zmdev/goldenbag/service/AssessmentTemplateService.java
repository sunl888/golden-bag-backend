package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.AssessmentProject;
import com.zmdev.goldenbag.domain.AssessmentProjectItem;
import com.zmdev.goldenbag.domain.AssessmentTemplate;

import java.util.List;

public interface AssessmentTemplateService extends BaseService<AssessmentTemplate, Long> {

    AssessmentTemplate saveTemplate(AssessmentTemplate template);

    AssessmentTemplate updateTemplate(Long id, AssessmentTemplate template);

    List<AssessmentTemplate> findByType(AssessmentTemplate.Type type);

    AssessmentProject saveProject(Long templateId, AssessmentProject project);

    AssessmentProject updateProject(Long projectId, AssessmentProject project);

    AssessmentProjectItem saveProjectItem(Long projectId, AssessmentProjectItem projectItem);

    AssessmentProjectItem updateProjectItem(Long projectItemId, AssessmentProjectItem projectItem);
}
