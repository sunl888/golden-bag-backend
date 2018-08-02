package com.zmdev.goldenbag.web.insterceptor;

import com.zmdev.goldenbag.domain.Quarter;
import com.zmdev.goldenbag.exception.ModelNotFoundException;
import com.zmdev.goldenbag.exception.NotInDateOfExaminationException;
import com.zmdev.goldenbag.service.QuarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TemplateInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private QuarterService quarterService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String filterMethod = "PUT,PATCH,POST,DELETE";
        if (filterMethod.contains(request.getMethod())) {
            Quarter currentQuarter = quarterService.findCurrentQuarter();
            if (currentQuarter == null) {
                throw new ModelNotFoundException("没有设置当前季度");
            }
            // 判断有没有到考核时间
            Date startAssessmentDate = currentQuarter.getStartAssessmentDate();
            Date currentDate = new Date();
            if (startAssessmentDate.compareTo(currentDate) <= 0) {
                throw new NotInDateOfExaminationException("考核期内不可以编辑模板");
            }
        }
        return true;
    }
}
