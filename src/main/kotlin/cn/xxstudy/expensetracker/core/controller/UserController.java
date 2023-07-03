package cn.xxstudy.expensetracker.core.controller;

import cn.xxstudy.expensetracker.constant.HttpCode;
import cn.xxstudy.expensetracker.core.services.user.UserService;
import cn.xxstudy.expensetracker.data.Response;
import cn.xxstudy.expensetracker.data.model.RequestLoginModel;
import cn.xxstudy.expensetracker.data.model.RequestUserModel;
import cn.xxstudy.expensetracker.data.model.ResponseUserModel;
import cn.xxstudy.expensetracker.data.model.Token;
import cn.xxstudy.expensetracker.data.table.User;
import cn.xxstudy.expensetracker.utils.TokenHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date: 2023/7/3 20:56
 * @author: LovelyCoder
 * @remark:
 */
@RestController
public class UserController {

    private final UserService userService;

    private final TokenHelper tokenHelper;

    public UserController(UserService userService, TokenHelper tokenHelper) {
        this.userService = userService;
        this.tokenHelper = tokenHelper;
    }

    @PostMapping("/register")
    public Response<User>register(@RequestBody @Validated RequestUserModel model){
        User user = userService.register(model);
        if(user==null) return Response.failed(HttpCode.REGISTER_ERROR);
        return Response.success(user);
    }

    @PostMapping("/login")
    public Response<ResponseUserModel>login(@RequestBody @Validated RequestLoginModel model){
        User user = userService.login(model);
        if(user==null)return Response.failed(HttpCode.LOGIN_ERROR);

        ResponseUserModel userModel = new ResponseUserModel(user);
        userModel.setToken(new Token(tokenHelper.generateAccessToken(user.getId()),tokenHelper.generateRefreshToken(user.getId())));
        return Response.success(userModel);
    }
}
