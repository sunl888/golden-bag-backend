package com.zmdev.goldenbag.schedules;

import com.zmdev.goldenbag.domain.*;
import com.zmdev.goldenbag.service.AssessmentInputService;
import com.zmdev.goldenbag.service.AssessmentProjectService;
import com.zmdev.goldenbag.service.AssessmentTemplateService;
import com.zmdev.goldenbag.service.QuarterService;
import com.zmdev.goldenbag.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutoGenerationQuarter {
    // 默认单价
    private static final double DEFAULTPRICE = 50.0;
    private QuarterService quarterService;
    private AssessmentTemplateService assessmentTemplateService;
    @Autowired
    private AssessmentProjectService assessmentProjectService;
    @Autowired
    private AssessmentInputService assessmentInputService;
    @Autowired
    private AssessmentProjectItemRepository assessmentProjectItemRepository;

    @Autowired
    public void setQuarterController(QuarterService quarterService) {
        this.quarterService = quarterService;
    }

    @Autowired
    public void setAssessmentTemplateService(AssessmentTemplateService assessmentTemplateService) {
        this.assessmentTemplateService = assessmentTemplateService;
    }

    /**
     * At: http://cron.qqe2.com
     * <p>
     * Scheduled(cron = "0 0/1 * * * ?")// 一分钟测试
     * <p>
     * 最近启动时间：
     * 2018/10/1 1:01:00
     * 2019/1/1 1:01:00
     * 2019/4/1 1:01:00
     */
    @Scheduled(cron = "0 1 1 1 1,4,7,10 ?")
    public void generator() {

        Quarter lastQuarter = quarterService.findCurrentQuarter();

        Quarter newQuarter = new Quarter();
        newQuarter.setCurrentQuarter(true);
        newQuarter.setName(TimeUtil.getCurrentYear() + "年第" + TimeUtil.getCurrentQuarter() + "季度");
        newQuarter.setStartDate(TimeUtil.getCurrentQuarterStartTime());
        newQuarter.setStartAssessmentDate(TimeUtil.getCurrentQuarterEndDate());
        if (lastQuarter.getPrice() == null) {
            newQuarter.setPrice(DEFAULTPRICE);
        } else {
            newQuarter.setPrice(lastQuarter.getPrice());
        }
        // save new quarter
        quarterService.save(newQuarter);

        // 复制上个季度的all模板到这个季度
        List<AssessmentTemplate> templatesWithLastQuarter = assessmentTemplateService.findByQuarter(lastQuarter);
        for (AssessmentTemplate assessmentTemplate : templatesWithLastQuarter) {
            assessmentTemplate.setQuarter(newQuarter);

            assessmentTemplate.setId(null);
            assessmentTemplate.setCreatedAt(null);
            assessmentTemplate.setUpdatedAt(null);
            assessmentTemplateService.save(assessmentTemplate);
            for (AssessmentInput input : assessmentTemplate.getAssessmentInputs()) {
                input.setId(null);
                input.setAssessmentTemplate(assessmentTemplate);
                assessmentInputService.save(input);
            }
            for (AssessmentProject project : assessmentTemplate.getAssessmentProjects()) {
                project.setId(null);
                project.setAssessmentTemplate(assessmentTemplate);
                assessmentProjectService.save(project);
                for (AssessmentProjectItem projectItem : project.getItems()) {
                    projectItem.setId(null);
                    projectItem.setAssessmentProject(project);
                    assessmentProjectItemRepository.save(projectItem);
                }
            }
        }
        // 其他的quarter 的current 设置为false
        lastQuarter.setCurrentQuarter(false);
        quarterService.save(lastQuarter);
    }
}
