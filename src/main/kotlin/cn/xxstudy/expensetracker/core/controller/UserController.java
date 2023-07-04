package cn.xxstudy.expensetracker.core.controller;

import cn.xxstudy.expensetracker.annotation.TokenRequired;
import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.constant.HttpCode;
import cn.xxstudy.expensetracker.core.services.user.UserService;
import cn.xxstudy.expensetracker.data.Response;
import cn.xxstudy.expensetracker.data.model.RequestLoginModel;
import cn.xxstudy.expensetracker.data.model.RequestUserModel;
import cn.xxstudy.expensetracker.data.model.ResponseUserModel;
import cn.xxstudy.expensetracker.data.model.Token;
import cn.xxstudy.expensetracker.data.table.TransactionExpense;
import cn.xxstudy.expensetracker.data.table.User;
import cn.xxstudy.expensetracker.utils.TokenHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Response<User> register(@RequestBody @Validated RequestUserModel model) {
        User user = userService.register(model);
        return Optional.ofNullable(user)
                .map(Response::success)
                .orElse(Response.failed(HttpCode.REGISTER_ERROR));
    }

    @PostMapping("/login")
    public Response<ResponseUserModel> login(@RequestBody @Validated RequestLoginModel model) {
        ResponseUserModel userModel = userService.login(model);
        return Optional.ofNullable(userModel)
                .map(Response::success)
                .orElse(Response.failed(HttpCode.LOGIN_ERROR));
    }

    @GetMapping("/refreshToken")
    public Response<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = userService.refreshToken(request, response);
        return Response.success(accessToken);
    }
}
