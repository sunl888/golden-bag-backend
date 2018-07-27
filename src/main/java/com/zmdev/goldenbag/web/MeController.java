package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.service.AssessmentService;
import com.zmdev.goldenbag.service.UserService;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/me")
public class MeController extends BaseController {

    private AssessmentService assessmentService;

    private UserService userService;

    private Auth auth;

    @Autowired
    public MeController(AssessmentService assessmentService, Auth auth, UserService userService) {
        this.auth = auth;
        this.userService = userService;
        this.assessmentService = assessmentService;
    }

    /**
     * 查看登录人员的下级员工，并给下级员工打分评价。
     *
     * @return Result
     */
    @RequestMapping("review_list")
    public Result reviewList() {
        User currentUser = auth.getUser();
        List<User> users = userService.findByDirectManager(currentUser);
        System.out.println(users);
//        assessmentService.findByUser(users);
        return ResultGenerator.genSuccessResult();
    }

}
