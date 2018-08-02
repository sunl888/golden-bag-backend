package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.domain.Quarter;
import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.exception.ModelNotFoundException;
import com.zmdev.goldenbag.service.AssessmentService;
import com.zmdev.goldenbag.service.QuarterService;
import com.zmdev.goldenbag.service.UserService;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/me")
public class MeController extends BaseController {

    private AssessmentService assessmentService;
    @Autowired
    private QuarterService quarterService;

    private UserService userService;

    private Auth auth;

    @Autowired
    public MeController(AssessmentService assessmentService, Auth auth, UserService userService) {
        this.auth = auth;
        this.userService = userService;
        this.assessmentService = assessmentService;
    }

    /**
     * 查看登录人员的下级员工(未评价的员工)，并给下级员工打分评价。
     *
     * @return Result
     */
    @GetMapping("review_list")
    public Result reviewList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        User currentUser = auth.getUser();

        Page<Map<String, Object>> assessments = null;
        List<User> users = userService.findByDirectManager(currentUser);
        if (users.size() > 0) {
            assessments = assessmentService.findByUserInAndStatus(
                    users,
                    Assessment.Status.SUBMITTED,
                    PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "updatedAt"))
            );
        }
        return ResultGenerator.genSuccessResult(assessments);
    }

    /**
     * 间接经理获取其需要评价的考核记录列表
     *
     * @param page int
     * @param size int
     * @return Result
     */
    @GetMapping("comment_list")
    public Result commentList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        User currentUser = auth.getUser();
        Page<Map<String, Object>> assessments = null;
        List<User> users = userService.findByIndirectManager(currentUser);
        if (users.size() > 0) {
            assessments = assessmentService.findByUserInAndStatus(
                    users,
                    Assessment.Status.DIRECT_MANAGER_EVALUATED,
                    PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "updatedAt"))
            );
        }
        return ResultGenerator.genSuccessResult(assessments);
    }

    /**
     * 获取当前登陆的用户
     *
     * @return Result
     */
    @GetMapping
    public Result me() {
        User user = auth.getUser();

        // 获取当前季度
        Quarter currentQuarter = quarterService.findCurrentQuarter();
        if (currentQuarter == null) {
            throw new ModelNotFoundException("没有设置当前季度");
        }
        // 用户当前季度提交状态
        boolean isSubmited = assessmentService.isSubmitedWithCurrentQuarter(user, currentQuarter);

        return ResultGenerator.genSuccessResult(user)
                .addMeta("is_submited", isSubmited);
    }
}
