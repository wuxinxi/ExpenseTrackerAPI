package cn.xxstudy.expensetracker.core.services.user;

import cn.xxstudy.expensetracker.core.mapper.UserMapper;
import cn.xxstudy.expensetracker.data.model.RequestLoginModel;
import cn.xxstudy.expensetracker.data.model.RequestUserModel;
import cn.xxstudy.expensetracker.data.table.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Nullable
    public User register(RequestUserModel userModel) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserEmail, userModel.getUserEmail());
        if (userMapper.exists(queryWrapper)) {
            return null;
        }
        User insertUser = userModel.toUser();
        int row = userMapper.insert(insertUser);
        if (row > 0) return userMapper.selectById(insertUser.getId());
        return null;
    }

    @Override
    @Nullable
    public User login(RequestLoginModel loginModel) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserEmail, loginModel.getUserEmail()).eq(User::getUserPassword, loginModel.getUserPassword());
        return userMapper.selectOne(queryWrapper);
    }
}
