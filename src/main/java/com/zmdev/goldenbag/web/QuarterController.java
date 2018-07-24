package com.zmdev.goldenbag.web;


import com.zmdev.goldenbag.domain.result.Response;
import com.zmdev.goldenbag.domain.result.ResponseData;
import com.zmdev.goldenbag.service.QuarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quarter")
public class QuarterController extends BaseController {
    private QuarterService quarterService;

    public QuarterController(@Autowired QuarterService quarterService) {
        this.quarterService = quarterService;
    }

    @GetMapping
    public Response index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
       return new ResponseData(quarterService.findAllByPage(
            PageRequest.of(page, size,
                    new Sort(Sort.Direction.DESC, "startDate")
            )
       ));
    }
}
