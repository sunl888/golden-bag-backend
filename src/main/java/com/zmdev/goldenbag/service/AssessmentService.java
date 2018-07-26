package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.domain.Quarter;
import com.zmdev.goldenbag.domain.User;

public interface AssessmentService extends BaseService<Assessment, Long> {

    /**
     * 提交考核记录
     *
     * @param assessment
     * @param creator
     */
    void storeAssessment(Assessment assessment, User creator);

    /**
     * 直接经理评分
     *
     * @param assessment
     * @param assessmentId
     * @param operater
     */
    void directManagerScore(Assessment assessment, Long assessmentId, User operater);

    /**
     * 间接经理提出建议
     *
     * @param assessment
     * @param operator
     */
    void indirectManagerAuditComments(Assessment assessment, Long assessmentId, User operator);

    /**
     * 当前季度指定用户有没有提交考核
     *
     * @param user
     * @param quarter
     * @return
     */
    boolean isSubmitedWithCurrentQuarter(User user, Quarter quarter);

}

