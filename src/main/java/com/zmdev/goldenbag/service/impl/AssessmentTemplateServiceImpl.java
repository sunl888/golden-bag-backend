package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.*;
import com.zmdev.goldenbag.exception.ModelNotFoundException;
import com.zmdev.goldenbag.service.AssessmentTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentTemplateServiceImpl extends BaseServiceImpl<AssessmentTemplate, Long, AssessmentTemplateRepository> implements AssessmentTemplateService {

    private AssessmentProjectRepository assessmentProjectRepository;
    private AssessmentProjectItemRepository assessmentProjectItemRepository;

    public AssessmentTemplateServiceImpl(@Autowired AssessmentProjectRepository assessmentProjectRepository) {
        this.assessmentProjectRepository = assessmentProjectRepository;
    }

    @Override
    public void saveTemplate(AssessmentTemplate template) {
        // 复制一个模板
        AssessmentTemplate lastTemplate = repository.getLast();
        lastTemplate.setId(null);
        lastTemplate.setName(template.getName());
        lastTemplate.setCreatedAt(null);
        lastTemplate.setUpdatedAt(null);
        lastTemplate.setQuarter(template.getQuarter());
        for (AssessmentInput input : lastTemplate.getAssessmentInputs()) {
            input.setId(null);
        }
        for (AssessmentProject project : lastTemplate.getAssessmentProjects()) {
            project.setId(null);
            for (AssessmentProjectItem projectItem : project.getItems()) {
                projectItem.setId(null);
            }
        }
        repository.save(lastTemplate);
    }

    @Override
    public void updateTemplate(Long id, AssessmentTemplate template) {
        template.setId(id);
        repository.save(template);
    }

    @Override
    public List<AssessmentTemplate> findByType(AssessmentTemplate.Type type) {
        return repository.findByType(type);
    }

    public void saveProject(Long templateId, AssessmentProject project) {
        AssessmentTemplate template = repository.findById(templateId).orElse(null);
        if (template == null) {
            throw new ModelNotFoundException("模版不存在");
        }
        project.setAssessmentTemplate(template);
        assessmentProjectRepository.save(project);
    }

    public void updateProject(Long projectId, AssessmentProject project) {
        project.setId(projectId);
        assessmentProjectRepository.save(project);
    }

    public void saveProjectItem(Long projectId, AssessmentProjectItem projectItem) {
        AssessmentProject project = assessmentProjectRepository.findById(projectId).orElse(null);
        if (project == null) {
            throw new ModelNotFoundException("考核项目标准不存在");
        }
        projectItem.setAssessmentProject(project);
        assessmentProjectItemRepository.save(projectItem);
    }

    public void updateProjectItem(Long projectItemId, AssessmentProjectItem projectItem) {
        projectItem.setId(projectItemId);
        assessmentProjectItemRepository.save(projectItem);
    }
}
