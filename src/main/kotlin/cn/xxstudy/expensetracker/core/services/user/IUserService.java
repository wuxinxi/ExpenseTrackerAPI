package cn.xxstudy.expensetracker.core.services.user;

import cn.xxstudy.expensetracker.data.model.RequestLoginModel;
import cn.xxstudy.expensetracker.data.model.RequestUserModel;
import cn.xxstudy.expensetracker.data.model.ResponseUserModel;
import cn.xxstudy.expensetracker.data.table.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService extends IService<User> {
    @Nullable
    User register(RequestUserModel userModel);

    @Nullable
    ResponseUserModel login(RequestLoginModel loginModel);

    String refreshToken(HttpServletRequest request, HttpServletResponse response);

    boolean hasThrow(String email);
}
