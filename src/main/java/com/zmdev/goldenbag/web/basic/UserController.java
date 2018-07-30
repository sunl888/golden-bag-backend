package com.zmdev.goldenbag.web.basic;

import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.service.UserService;
import com.zmdev.goldenbag.web.BaseController;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    private UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Result index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResultGenerator.genSuccessResult(
                userService.findAllByPage(
                        PageRequest.of(page, size,
                                new Sort(Sort.Direction.DESC, "createdAt")
                        )
                )
        );
    }

    @GetMapping("/search")
    public Result search(@RequestParam(defaultValue = "") String keyword, @RequestParam(required = false) Long ignoreId) {
        return ResultGenerator.genSuccessResult(userService.search(keyword.trim(), ignoreId));
    }

    @GetMapping("/{id}")
    public Result show(@PathVariable Long id) {
        return ResultGenerator.genSuccessResult(userService.findById(id));
    }

    @PostMapping
    public Result store(@RequestBody User user) {
        user.setId(null);
        return ResultGenerator.genSuccessResult(userService.save(user));
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result update(@PathVariable Long id, @RequestBody User user) {
        user.setDepartment(null);
        return ResultGenerator.genSuccessResult(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public Result destroy(@PathVariable Long id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }
}
