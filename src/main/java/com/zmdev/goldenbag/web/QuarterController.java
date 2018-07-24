package com.zmdev.goldenbag.web;


import com.zmdev.goldenbag.domain.Quarter;
import com.zmdev.goldenbag.service.QuarterService;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/quarters")
public class QuarterController extends BaseController {
    private QuarterService quarterService;

    public QuarterController(@Autowired QuarterService quarterService) {
        this.quarterService = quarterService;
    }

    @GetMapping
    public Result<Page<Quarter>> index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResultGenerator.genSuccessResult(quarterService.findAllByPage(
            PageRequest.of(page, size,
                    new Sort(Sort.Direction.DESC, "startDate")
            )
       ));
    }

    @GetMapping("/{id}")
    public Result<Optional<Quarter>> show(@PathVariable Long id) {
        return ResultGenerator.genSuccessResult(quarterService.findById(id));
    }

    @PostMapping
    public Result store(@RequestBody Quarter quarter) {
        quarterService.save(quarter);
        return ResultGenerator.genSuccessResult();
    }

}
