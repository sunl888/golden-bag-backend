package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.*;
import com.zmdev.goldenbag.exception.*;
import com.zmdev.goldenbag.service.*;
import com.zmdev.goldenbag.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private AssessmentTemplateService assessmentTemplateService;

    /**
     * 用户填写并提交考核申请
     *
     * @param assessment
     * @param creator
     */
    public void storeAssessment(Assessment assessment, User creator) {
        // 获取当前季度
        Quarter currentQuarter = quarterService.findCurrentQuarter();
        if (currentQuarter.getId() == null) {
            throw new ModelNotFoundException("没有设置当前季度");
        }
        // 判断有没有到考核时间
        Date startAssessmentDate = currentQuarter.getStartAssessmentDate();
        Date currentDate = new Date();
        if (startAssessmentDate.compareTo(currentDate) > 0) {
            throw new NotInDateOfExaminationException("不在考核期内");
        }
        // 判断用户当前季度有没有提交
        boolean isSubmited = assessmentService.isSubmitedWithCurrentQuarter(creator, currentQuarter);
        if (isSubmited) {
            throw new AlreadySubmitedException("当前季度已经提交过考核申请了");
        }
        // 获取当前用户使用的模板
        AssessmentTemplate assessmentTemplate = assessmentTemplateService.findByTypeAndQuarter(creator.getType(), currentQuarter);
        if (assessmentTemplate.getId() == null) {
            throw new ModelNotFoundException("模板不存在");
        }
        assessment.setAssessmentTemplate(assessmentTemplate);
        assessment.setUser(creator);
        // 直接经理评价字段默认设置为空
        assessment.setIndirectManagerAuditComments(null);
        // 间接经理评价字段默认设置为空
        assessment.setDirectManagerEvaluation(null);
        // 考核记录状态设置为已提交
        assessment.setStatus(Assessment.Status.SUBMITTED);
        // 职级系数直接从用户表拿到
        Double rank = creator.getRankCoefficient();
        if (rank == null) {
            throw new ModelNotFoundException("职级系数为空");
        }
        assessment.setRankCoefficient(creator.getRankCoefficient());
        // 设置时间系数
        assessment.setTimeCoefficient(this.calcTimeCoefficient(creator));
        assessment.setQuarter(currentQuarter);
        assessment.setQuarterlyBonus(null);
        Long templateId = assessment.getAssessmentTemplate().getId();
        assessment.setTotalManagerScore(null);
        assessment.setTotalSelfScore(null);
        assessmentService.save(assessment);
        int selfTotalScore = 0;
        // save project
        for (AssessmentProjectScore assessmentProjectScore : assessment.getAssessmentProjectScores()) {
            Long projectId = assessmentProjectScore.getAssessmentProject().getId();
            Optional<AssessmentProject> project = assessmentProjectService.findById(projectId);
            Long id = project.get().getAssessmentTemplate().getId();
            // 判断每个项目是否属于传进来的模板
            if (!id.equals(templateId)) {
                // TODO log
                throw new ModelNotFoundException("该项目不属于本模板");
            }
            // 设置自己总评分
            selfTotalScore += assessmentProjectScore.getSelfScore();
            assessmentProjectScore.setManagerScore(null);
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
                throw new ModelNotFoundException("该项目不属于本模板");
            }
            assessmentInputContent.setAssessment(assessment);
            assessmentInputContentService.save(assessmentInputContent);
        }
        assessment.setTotalSelfScore(selfTotalScore);
        assessmentService.save(assessment);
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
     * <p>
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
        Assessment savedAssessment = null;
        try {
            // TODO 这里需要判断是不是当前季度提交的考核记录
            savedAssessment = assessmentService.findById(assessmentId).get();
        } catch (NoSuchElementException e) {
            throw new ModelNotFoundException("不存在该考核记录");
        }
        // 判断用户当前季度有没有提交
        Quarter currentQuarter = quarterService.findCurrentQuarter();
        if (currentQuarter.getId() <= 0) {
            throw new ModelNotFoundException("没有设置当前季度");
        }

        // 判断 Status
        switch (savedAssessment.getStatus()) {
            case SUBMITTED:
                break;
            case DIRECT_MANAGER_EVALUATED:// 直接经理已经评价
                throw new AlreadyScoreException("你已经评价过啦");
            case INDIRECT_MANAGER_RECHECK:// 间接经理已经评价
                throw new AlreadyScoreException("你已经评价过啦");
            case FINISHED:
                throw new FinishedException("考核已经完成啦");
            default:
                break;
        }

        User user = savedAssessment.getUser().getDirectManager();
        if (!(operater.equals(user))) {
            throw new PermissionDeniedException("没有评分该记录的权限");
        }
        int directManagerScore = 0;
        // 设置直接经理评价
        savedAssessment.setDirectManagerEvaluation(assessment.getDirectManagerEvaluation());
        savedAssessment.setStatus(Assessment.Status.DIRECT_MANAGER_EVALUATED);// Status 设置为直接经理已经评价
        System.out.println(assessment.getAssessmentProjectScores().size());

        if (assessment.getAssessmentProjectScores().size() <= 0) {
            throw new NoScoreException("请把表单数据填写完整在尝试重新提交");
        }
        for (AssessmentProjectScore assessmentProjectScore : assessment.getAssessmentProjectScores()) {
            AssessmentProject assessmentProject = assessmentProjectScore.getAssessmentProject();
            AssessmentProjectScore aps = assessmentProjectScoreService.findByAssessmentProjectAndAssessment(assessmentProject, savedAssessment);
            if (aps == null) {
                throw new ModelNotFoundException("考核项目不存在");
            }
            aps.setRemarks(assessmentProjectScore.getRemarks());
            aps.setManagerScore(assessmentProjectScore.getManagerScore());
            directManagerScore += assessmentProjectScore.getManagerScore();
            assessmentProjectScoreService.save(aps);
        }
        // 设置经理评分总和
        savedAssessment.setTotalManagerScore(directManagerScore);
        assessmentService.save(savedAssessment);
    }

    /**
     * 间接经理审核
     * <p>
     * 1.判断审核记录是否存在
     * 2.有没有评分权限
     * 3.判断直接经理有没有评分
     * 4.写入评分
     * 5.计算默认总分
     */
    public void indirectManagerAuditComments(Assessment assessment, Long assessmentId, User operater) {
        Assessment savedAssessment = null;
        try {
            // TODO 这里需要判断是不是当前季度提交的考核记录
            savedAssessment = assessmentService.findById(assessmentId).get();
        } catch (NoSuchElementException e) {
            throw new ModelNotFoundException("不存在该考核记录");
        }
        // 判断有没有权限
        User user = savedAssessment.getUser().getIndirectManager();
        if (!(operater.equals(user))) {
            throw new PermissionDeniedException("没有评分的权限o");
        }
        // 判断 Status
        switch (savedAssessment.getStatus()) {
            case SUBMITTED:
                throw new NoScoreException("直接经理还没有打分噢");
            case DIRECT_MANAGER_EVALUATED:
                break;
            case INDIRECT_MANAGER_RECHECK:
                throw new AlreadyScoreException("你已经评价过了");
            case FINISHED:
                throw new FinishedException("考核已经完成啦");
            default:
                break;
        }
        savedAssessment.setIndirectManagerAuditComments(assessment.getIndirectManagerAuditComments());
        // 这里直接可以设置状态为已完成
        // savedAssessment.setStatus(Assessment.Status.INDIRECT_MANAGER_RECHECK);
        savedAssessment.setQuarterlyBonus(this.calcQuarterlyBonus(savedAssessment));
        savedAssessment.setStatus(Assessment.Status.FINISHED);

        assessmentService.save(savedAssessment);
    }

    // 计算最终奖金
    // 季度奖金 = 职级系数*时间系数*最终评分(目前是直接使用经理总评分来计算的)*分数单价
    private double calcQuarterlyBonus(Assessment assessment) {
        // 获取当前季度
        Quarter currentQuarter = quarterService.findCurrentQuarter();
        if (currentQuarter.getId() == null) {
            throw new ModelNotFoundException("没有设置当前季度");
        }
        double price = currentQuarter.getPrice();
        double coefficient = assessment.getRankCoefficient() * assessment.getTimeCoefficient();
        double managerScore = (assessment.getTotalManagerScore() - 60);
        if (managerScore < 0) managerScore = 0;
        return coefficient * managerScore * price;
    }

    /**
     * 计算时间系数
     * 时间系数=（当前季度末时间-入职时间）/季度总天数   系数小于0.3 则调整为0，系数大于1.0则调整为1.0
     *
     * @param creator
     * @return double
     */
    private double calcTimeCoefficient(User creator) {
        // 获取当前季度
        Quarter currentQuarter = quarterService.findCurrentQuarter();
        if (currentQuarter == null) {
            throw new ModelNotFoundException("没有设置当前季度");
        }
        Date workAt = creator.getEntryDate();
        Date currentQuarterEndTime = TimeUtil.getCurrentQuarterEndTime();
        int countDays = TimeUtil.getCurrentQuarterDays();
        /* 先转成毫秒并求差 */
        long differDays = Math.abs(workAt.getTime() - currentQuarterEndTime.getTime()) / (1000 * 60 * 60 * 24);
        double timeCoefficient = (double) (differDays / countDays);
        if (timeCoefficient > 1) timeCoefficient = 1;
        if (timeCoefficient < 0.3) timeCoefficient = 0;

        System.out.println("工作时间:" + workAt);
        System.out.println("当前季度末时间:" + currentQuarterEndTime);
        System.out.println("当前季度总天数:" + countDays);

        return timeCoefficient;
    }

    public List<Assessment> findByUserIn(Collection<User> users) {
        return repository.findByUserIn(users);
    }

    public List<Assessment> findByQuarter(Quarter quarter) {
        return repository.findByQuarter(quarter);
    }

    public Page<Assessment> findByQuarter(Quarter quarter, Pageable pageable) {
        return repository.findByQuarter(quarter, pageable);
    }

    public List<Assessment> findByUser(User user) {
        return repository.findByUser(user);
    }

    public List<Assessment> queryAllWithCurrentQuarterWaitAudit(Collection<User> users, Pageable pageable) {
        return repository.queryAllWithCurrentQuarterWaitAudit(users, pageable);
    }

    public Page<Map<String, Object>> findByUserInAndStatus(Collection<User> users, Assessment.Status status, Pageable pageable) {
        return repository.selectByUserInAndStatusIs(users, status, pageable);
    }

    public List<Assessment> findByIds(Long[] ids) {
        return repository.findByIdWhereIn(ids);
    }
}