package cn.xxstudy.expensetracker.core.services.user;

import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.constant.ErrorCode;
import cn.xxstudy.expensetracker.core.mapper.UserMapper;
import cn.xxstudy.expensetracker.data.bean.TokenData;
import cn.xxstudy.expensetracker.data.model.RequestLoginModel;
import cn.xxstudy.expensetracker.data.model.RequestUserModel;
import cn.xxstudy.expensetracker.data.model.ResponseUserModel;
import cn.xxstudy.expensetracker.data.model.Token;
import cn.xxstudy.expensetracker.data.table.User;
import cn.xxstudy.expensetracker.global.exception.AppException;
import cn.xxstudy.expensetracker.global.exception.TokenException;
import cn.xxstudy.expensetracker.utils.TokenHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    private final TokenHelper tokenHelper;

    public UserService(UserMapper userMapper, TokenHelper tokenHelper) {
        this.userMapper = userMapper;
        this.tokenHelper = tokenHelper;
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
    public ResponseUserModel login(RequestLoginModel loginModel) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserEmail, loginModel.getUserEmail()).eq(User::getUserPassword, loginModel.getUserPassword());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }
        ResponseUserModel userModel = new ResponseUserModel(user);
        userModel.setToken(new Token(tokenHelper.generateAccessToken(user.getId()), tokenHelper.generateRefreshToken(user.getId())));
        return userModel;
    }

    @Override
    @Nullable
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        if (StringUtils.isBlank(authorization)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new TokenException(ErrorCode.TOKEN_ERROR.getCode(), "缺少Authorization请求头");
        }
        TokenData tokenData;
        try {
            tokenData = tokenHelper.parseToken(authorization);
        } catch (ExpiredJwtException e) {
            throw new TokenException(ErrorCode.REFRESH_TOKEN_ERROR.getCode(), ErrorCode.REFRESH_TOKEN_ERROR.getMessage());
        }
        return Optional.of(tokenData)
                .filter(TokenData::isValidRefreshToken)
                .map(data -> tokenHelper.generateAccessToken(data.getId()))
                .orElseThrow(() -> new TokenException(ErrorCode.REFRESH_TOKEN_ERROR.getCode(), ErrorCode.REFRESH_TOKEN_ERROR.getMessage()));
    }

    @Override
    public boolean hasThrow(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserEmail, email);
        boolean exists = userMapper.exists(queryWrapper);
        if (exists) {
            throw new AppException(ErrorCode.REGISTER_ERROR);
        }
        return false;
    }
}
