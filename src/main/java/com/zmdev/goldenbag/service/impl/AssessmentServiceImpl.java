package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.*;
import com.zmdev.goldenbag.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AssessmentServiceImpl extends BaseServiceImpl<Assessment, Long, AssessmentRepository> implements AssessmentService {

    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private AssessmentProjectScoreService assessmentProjectScoreService;
    @Autowired
    private AssessmentInputContentService assessmentInputContentService;
    @Autowired
    private AssessmentProjectService assessmentProjectService;
    @Autowired
    private AssessmentInputService assessmentInputService;

    public void storeAssessment(Assessment assessment, User creator) {
        assessment.setUser(creator);
        // 直接经理评价字段默认设置为空
        assessment.setIndirectManagerAuditComments(null);
        // 间接经理评价字段默认设置为空
        assessment.setDirectManagerEvaluation(null);
        // 考核记录状态设置为已提交
        assessment.setStatus(Assessment.Status.SUBMITTED);
        // 职级系数直接从用户表拿到
        assessment.setRankCoefficient(creator.getRankCoefficient());

        assessmentService.save(assessment);

        Long templateId = assessment.getAssessmentTemplate().getId();

        // save project
        for (AssessmentProjectScore assessmentProjectScore : assessment.getAssessmentProjectScores()) {
            Long projectId = assessmentProjectScore.getAssessmentProject().getId();
            Optional<AssessmentProject> project = assessmentProjectService.findById(projectId);
            Long id = project.get().getAssessmentTemplate().getId();
            // 判断每个项目是否属于传进来的模板
            if (!id.equals(templateId)) {
                continue;
            }
            assessmentProjectScore.setManagerScore(0);
            assessmentProjectScore.setRemarks(null);
            assessmentProjectScore.setAssessment(assessment);
            assessmentProjectScoreService.save(assessmentProjectScore);
        }
        // save input
        for (AssessmentInputContent assessmentInputContent : assessment.getAssessmentInputContents()) {
            Long inputId = assessmentInputContent.getAssessmentInput().getId();
            Optional<AssessmentInput> input = assessmentInputService.findById(inputId);
            Long id = input.get().getAssessmentTemplate().getId();
            if (!id.equals(templateId)) {
                continue;
            }
            assessmentInputContent.setAssessment(assessment);
            assessmentInputContentService.save(assessmentInputContent);
        }
    }
}
