package cn.xxstudy.expensetracker.core.controller;

import cn.xxstudy.expensetracker.constant.ErrorCode;
import cn.xxstudy.expensetracker.core.services.email.EmailService;
import cn.xxstudy.expensetracker.core.services.user.UserService;
import cn.xxstudy.expensetracker.data.Response;
import cn.xxstudy.expensetracker.data.model.RequestLoginModel;
import cn.xxstudy.expensetracker.data.model.RequestUserModel;
import cn.xxstudy.expensetracker.data.model.ResponseUserModel;
import cn.xxstudy.expensetracker.data.table.User;
import cn.xxstudy.expensetracker.global.exception.AppException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @date: 2023/7/3 20:56
 * @author: LovelyCoder
 * @remark:
 */
@RestController
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/getVerifyCode")
    public Response getVerifyCode(@RequestParam String email) {
        userService.hasThrow(email);
        emailService.generateVerifyCode(email);
        return Response.success();
    }


    @PostMapping("/register")
    public Response<User> register(@RequestBody @Validated RequestUserModel model) {
        boolean exist = emailService.checkVerifyCode(model.getUserEmail(), model.getVerifyCode());
        if (!exist) {
            throw new AppException(-1, "验证码错误");
        }
        User user = userService.register(model);
        return Optional.ofNullable(user)
                .map(Response::success)
                .orElse(Response.failed(ErrorCode.REGISTER_ERROR));
    }

    @PostMapping("/login")
    public Response<ResponseUserModel> login(@RequestBody @Validated RequestLoginModel model) {
        ResponseUserModel userModel = userService.login(model);
        return Optional.ofNullable(userModel)
                .map(Response::success)
                .orElse(Response.failed(ErrorCode.LOGIN_ERROR));
    }

    @GetMapping("/refreshToken")
    public Response<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = userService.refreshToken(request, response);
        return Response.success(accessToken);
    }
}
