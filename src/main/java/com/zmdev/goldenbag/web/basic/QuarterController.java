package com.zmdev.goldenbag.web.basic;


import com.zmdev.goldenbag.domain.Quarter;
import com.zmdev.goldenbag.exception.AllowUpdateException;
import com.zmdev.goldenbag.exception.ModelNotFoundException;
import com.zmdev.goldenbag.exception.NotInDateOfExaminationException;
import com.zmdev.goldenbag.service.QuarterService;
import com.zmdev.goldenbag.web.BaseController;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/quarters")
public class QuarterController extends BaseController {
    private QuarterService quarterService;

    public QuarterController(@Autowired QuarterService quarterService) {
        this.quarterService = quarterService;
    }

    @GetMapping
    public Result index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResultGenerator.genSuccessResult(quarterService.findAllByPage(
                PageRequest.of(page, size,
                        new Sort(Sort.Direction.DESC, "startDate")
                )
        ));
    }

    @GetMapping("/{id}")
    public Result show(@PathVariable Long id) {
        return ResultGenerator.genSuccessResult(quarterService.findById(id));
    }

    @PostMapping
    public Result store(@RequestBody Quarter quarter) {
        quarter.setId(null);
        quarter.setCurrentQuarter(false);// 默认不是当前季度
        quarterService.save(quarter);
        return ResultGenerator.genSuccessResult(quarter);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result update(@PathVariable Long id, Quarter quarter) {
        Quarter currentQuarter = quarterService.findById(id).orElseThrow(
                () -> new ModelNotFoundException("季度不存在")
        );
        if (!currentQuarter.getCurrentQuarter()) {
            throw new AllowUpdateException("只能编辑当前季度");
        }
        // 判断有没有到考核时间
        Date startAssessmentDate = currentQuarter.getStartAssessmentDate();
        Date currentDate = new Date();
        if (startAssessmentDate.compareTo(currentDate) <= 0) {
            throw new NotInDateOfExaminationException("考核期内不可以编辑季度");
        }
        quarter.setId(id);
        return ResultGenerator.genSuccessResult(quarterService.save(quarter));
    }
}
