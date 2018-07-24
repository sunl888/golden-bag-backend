package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.domain.result.Response;
import com.zmdev.goldenbag.domain.result.ResponseData;
import com.zmdev.goldenbag.service.UserService;
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
    public Response index(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {

        return new ResponseData(
                userService.findAllByPage(
                        PageRequest.of(page, size,
                                new Sort(Sort.Direction.DESC, "createdAt")
                        )
                )
        );
    }

    @GetMapping("/{id}")
    public Response show(@PathVariable Long id){
        return new ResponseData(userService.findById(id));
    }

    @PostMapping
    public Response store(@RequestBody User user) {
        userService.save(user);
        return new Response();
    }

    @DeleteMapping("/{id}")
    public Response destroy(@PathVariable Long id) {
        userService.deleteById(id);
        return new Response();
    }
}
