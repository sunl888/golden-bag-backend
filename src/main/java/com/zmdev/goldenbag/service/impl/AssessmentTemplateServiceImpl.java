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
    private AssessmentInputRepository assessmentInputRepository;

    public AssessmentTemplateServiceImpl(@Autowired AssessmentProjectRepository assessmentProjectRepository, @Autowired AssessmentProjectItemRepository assessmentProjectItemRepository, @Autowired AssessmentInputRepository assessmentInputRepository) {
        this.assessmentProjectRepository = assessmentProjectRepository;
        this.assessmentProjectItemRepository = assessmentProjectItemRepository;
        this.assessmentInputRepository = assessmentInputRepository;
    }

    @Override
    public AssessmentTemplate saveTemplate(AssessmentTemplate template) {
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

        return repository.save(lastTemplate);
    }

    @Override
    public AssessmentTemplate updateTemplate(Long id, AssessmentTemplate template) {
        template.setId(id);
        return repository.save(template);
    }

    @Override
    public List<AssessmentTemplate> findByType(AssessmentTemplate.Type type) {
        return repository.findByType(type);
    }

    public AssessmentProject saveProject(Long templateId, AssessmentProject project) {
        AssessmentTemplate template = repository.findById(templateId).orElse(null);
        if (template == null) {
            throw new ModelNotFoundException("模版不存在");
        }
        project.setAssessmentTemplate(template);
        return assessmentProjectRepository.save(project);
    }

    public AssessmentProject updateProject(Long projectId, AssessmentProject project) {
        project.setId(projectId);
        AssessmentProject oldAssessmentProject = assessmentProjectRepository.findById(projectId).orElse(null);

        // 将原本的父级设置上去
        if (oldAssessmentProject != null) {
            project.setAssessmentTemplate(oldAssessmentProject.getAssessmentTemplate());
        }

        return assessmentProjectRepository.save(project);
    }

    public AssessmentProjectItem saveProjectItem(Long projectId, AssessmentProjectItem projectItem) {
        AssessmentProject project = assessmentProjectRepository.findById(projectId).orElse(null);

        if (project == null) {
            throw new ModelNotFoundException("考核项目标准不存在");
        }

        projectItem.setAssessmentProject(project);
        return assessmentProjectItemRepository.save(projectItem);
    }

    public AssessmentProjectItem updateProjectItem(Long projectItemId, AssessmentProjectItem projectItem) {
        projectItem.setId(projectItemId);
        AssessmentProjectItem oldProjectItem = assessmentProjectItemRepository.findById(projectItemId).orElse(null);

        // 将原本的父级设置上去
        if (oldProjectItem != null) {

            if (0 == projectItem.getScore()) {
                projectItem.setScore(oldProjectItem.getScore());
            }
            if (projectItem.getTitle() == null || projectItem.getTitle().equals("")) {
                projectItem.setTitle(oldProjectItem.getTitle());
            }

            projectItem.setAssessmentProject(oldProjectItem.getAssessmentProject());
        }

        return assessmentProjectItemRepository.save(projectItem);
    }

    public AssessmentInput saveTemplateInput(Long templateId, AssessmentInput assessmentInput) {
        assessmentInput.setId(null);
        assessmentInput.setAssessmentTemplate(repository.findById(templateId).orElse(null));
        return assessmentInputRepository.save(assessmentInput);
    }

    public AssessmentInput updateTemplateInput(Long inputId, AssessmentInput assessmentInput) {
        assessmentInput.setId(inputId);
        AssessmentInput oldTemplateInput = assessmentInputRepository.findById(inputId).orElse(null);

        // 将原本的父级设置上去
        if (oldTemplateInput != null) {
            assessmentInput.setAssessmentTemplate(oldTemplateInput.getAssessmentTemplate());
        }

        return assessmentInputRepository.save(assessmentInput);
    }

}
