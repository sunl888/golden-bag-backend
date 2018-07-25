package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.AssessmentProject;
import com.zmdev.goldenbag.domain.AssessmentProjectItem;
import com.zmdev.goldenbag.domain.AssessmentTemplate;

import java.util.List;

public interface AssessmentTemplateService extends BaseService<AssessmentTemplate, Long> {
    List<AssessmentTemplate> findByType(AssessmentTemplate.Type type);

    void saveProject(Long templateId, AssessmentProject project);

    void updateProject(Long projectId, AssessmentProject project);

    void saveProjectItem(Long projectId, AssessmentProjectItem projectItem);

    void updateProjectItem(Long projectItemId, AssessmentProjectItem projectItem);
}
