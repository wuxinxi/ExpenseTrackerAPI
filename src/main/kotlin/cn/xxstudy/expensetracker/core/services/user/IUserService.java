package cn.xxstudy.expensetracker.core.services.user;

import cn.xxstudy.expensetracker.data.model.RequestLoginModel;
import cn.xxstudy.expensetracker.data.model.RequestUserModel;
import cn.xxstudy.expensetracker.data.table.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jetbrains.annotations.Nullable;

public interface IUserService extends IService<User> {
    @Nullable
    User register(RequestUserModel userModel);

    @Nullable
    User login(RequestLoginModel loginModel);
}
