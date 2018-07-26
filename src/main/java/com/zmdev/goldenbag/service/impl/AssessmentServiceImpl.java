package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.*;
import com.zmdev.goldenbag.exception.AlreadySubmitedException;
import com.zmdev.goldenbag.exception.ModelNotFoundException;
import com.zmdev.goldenbag.exception.PermissionDeniedException;
import com.zmdev.goldenbag.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
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
    @Autowired
    private QuarterService quarterService;

    /**
     * 用户填写并提交考核申请
     *
     * @param assessment
     * @param creator
     */
    public void storeAssessment(Assessment assessment, User creator) {
        // 判断用户当前季度有没有提交
        Quarter currentQuarter = quarterService.findCurrentQuarter();
        if (currentQuarter.getId() == null) {
            throw new ModelNotFoundException("没有设置当前季度");
        }

        boolean isSubmited = assessmentService.isSubmitedWithCurrentQuarter(creator, currentQuarter);

        if (isSubmited) {
            throw new AlreadySubmitedException("当前季度已经提交过考核申请了");
        }

        assessment.setUser(creator);
        // 直接经理评价字段默认设置为空
        assessment.setIndirectManagerAuditComments(null);
        // 间接经理评价字段默认设置为空
        assessment.setDirectManagerEvaluation(null);
        // 考核记录状态设置为已提交
        assessment.setStatus(Assessment.Status.SUBMITTED);
        // 职级系数直接从用户表拿到
        assessment.setRankCoefficient(creator.getRankCoefficient());

        assessment.setQuarter(currentQuarter);

        assessmentService.save(assessment);

        Long templateId = assessment.getAssessmentTemplate().getId();

        // save project
        for (AssessmentProjectScore assessmentProjectScore : assessment.getAssessmentProjectScores()) {
            Long projectId = assessmentProjectScore.getAssessmentProject().getId();
            Optional<AssessmentProject> project = assessmentProjectService.findById(projectId);
            Long id = project.get().getAssessmentTemplate().getId();
            // 判断每个项目是否属于传进来的模板
            if (!id.equals(templateId)) {
                // TODO log
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
            // 判断每个项目是否属于传进来的模板
            if (!id.equals(templateId)) {
                continue;
            }
            assessmentInputContent.setAssessment(assessment);
            assessmentInputContentService.save(assessmentInputContent);
        }
    }

    /**
     * 判断当前季度指定用户有没有提交过考核申请
     *
     * @param user
     * @param quarter
     * @return boolean
     */
    public boolean isSubmitedWithCurrentQuarter(User user, Quarter quarter) {
        return repository.isSubmitedWithCurrentQuarter(user, quarter) > 0;
    }

    /**
     * 直接经理评分
     * 1. 该考核记录是否存在
     * 2. 是否已经评分过了
     * 3. 有没有权限评分
     * 4. 对某些字段需要手动过滤
     * 5. 写入评分记录
     *
     * @param assessment
     * @param assessmentId
     */
    public void directManagerScore(Assessment assessment, Long assessmentId, User operater) {
        Assessment savedSsessment = null;
        try {
            savedSsessment = assessmentService.findById(assessmentId).get();
        } catch (NoSuchElementException e) {
            throw new ModelNotFoundException("不存在该考核记录");
        }
        // 判断用户当前季度有没有提交
        Quarter currentQuarter = quarterService.findCurrentQuarter();
        if (currentQuarter.getId() <= 0) {
            throw new ModelNotFoundException("没有设置当前季度");
        }
        /*boolean isQuarter = savedSsessment.getQuarter().getId().equals(currentQuarter.getId());
        if (!isQuarter) {
            throw new Exception("只能对本季度的记录评价");
        }*/
        if ((savedSsessment.getDirectManagerEvaluation() != null)) {
            throw new ModelNotFoundException("你已经评价过啦");
        }
        User user = savedSsessment.getUser().getDirectManager();
        if (!(operater.equals(user))) {
            throw new PermissionDeniedException("没有评分该记录的权限");
        }
        // 设置直接经理评价
        savedSsessment.setDirectManagerEvaluation(assessment.getDirectManagerEvaluation());
        assessmentService.save(savedSsessment);
        for (AssessmentProjectScore assessmentProjectScore : assessment.getAssessmentProjectScores()) {
            AssessmentProject assessmentProject = assessmentProjectScore.getAssessmentProject();
            AssessmentProjectScore aps = assessmentProjectScoreService.findByAssessmentProjectAndAssessment(assessmentProject, savedSsessment);
            aps.setRemarks(assessmentProjectScore.getRemarks());
            aps.setManagerScore(assessmentProjectScore.getManagerScore());
            assessmentProjectScoreService.save(aps);
        }
    }

    /**
     * 间接经理审核
     * <p>
     * 判断审核记录是否存在
     * 判断直接经理有没有评分
     * 有没有评分权限
     * 写入评分
     * 计算默认总分
     */
    public void indirectManagerAuditComments(Assessment assessment, Long assessmentId, User operator) {

    }
}
