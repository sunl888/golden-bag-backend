package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.*;
import com.zmdev.goldenbag.exception.ModelNotFoundException;
import com.zmdev.goldenbag.service.AssessmentTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssessmentTemplateServiceImpl extends BaseServiceImpl<AssessmentTemplate, Long, AssessmentTemplateRepository> implements AssessmentTemplateService {

    private AssessmentProjectRepository assessmentProjectRepository;
    private AssessmentProjectItemRepository assessmentProjectItemRepository;
    private AssessmentInputRepository assessmentInputRepository;
    private AssessmentTemplateRepository assessmentTemplateRepository;

    public AssessmentTemplateServiceImpl(@Autowired AssessmentProjectRepository assessmentProjectRepository, @Autowired AssessmentProjectItemRepository assessmentProjectItemRepository, @Autowired AssessmentInputRepository assessmentInputRepository) {
        this.assessmentProjectRepository = assessmentProjectRepository;
        this.assessmentProjectItemRepository = assessmentProjectItemRepository;
        this.assessmentInputRepository = assessmentInputRepository;
    }

    @Autowired
    public void setAssessmentTemplateRepository(AssessmentTemplateRepository assessmentTemplateRepository) {
        this.assessmentTemplateRepository = assessmentTemplateRepository;
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
    public Map<Integer, List<AssessmentTemplate>> findByType(AssessmentTemplate.Type type) {
        List<AssessmentTemplate> templates;
        if (type == null) {
            templates = repository.findAll();
        } else {
            templates = repository.findByType(type);
        }
        Map<Integer, List<AssessmentTemplate>> res = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        for (AssessmentTemplate template : templates) {
            Date startDate = template.getQuarter().getStartDate();
            cal.setTime(startDate);
            int year = cal.get(Calendar.YEAR);
            List<AssessmentTemplate> yearTemplates = res.get(year);
            if (yearTemplates == null) {
                ArrayList<AssessmentTemplate> arr = new ArrayList<>();
                arr.add(template);
                res.put(year, arr);
            } else {
                yearTemplates.add(template);
            }
        }
        return res;
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

    public void deleteProject(Long projectId) {
        Optional<AssessmentProject> assessmentProject = assessmentProjectRepository.findById(projectId);
        assessmentProjectItemRepository.deleteByAssessmentProject(assessmentProject.orElseThrow(
                () -> new ModelNotFoundException("不存在该项目")
        ));
        assessmentProjectRepository.deleteById(projectId);
    }

    public void deleteProjectItem(Long projectItemId) {
        assessmentProjectItemRepository.deleteById(projectItemId);
    }

    public void deleteTemplateInput(Long templateInputId) {
        assessmentInputRepository.deleteById(templateInputId);
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

    public AssessmentTemplate findByTypeAndQuarter(AssessmentTemplate.Type type, Quarter quarter) {
        return assessmentTemplateRepository.findByTypeAndQuarter(type, quarter);
    }

    public List<AssessmentTemplate> findByQuarter(Quarter quarter) {
        return assessmentTemplateRepository.findByQuarter(quarter);
    }
}
