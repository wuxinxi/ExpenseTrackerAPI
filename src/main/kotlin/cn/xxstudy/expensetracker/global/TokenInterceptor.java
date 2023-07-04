package cn.xxstudy.expensetracker.global;

import cn.xxstudy.expensetracker.annotation.TokenRequired;
import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.global.exception.TokenException;
import cn.xxstudy.expensetracker.utils.TokenHelper;
import cn.xxstudy.expensetracker.utils.TokenType;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @date: 2023/7/3 21:04
 * @author: LovelyCoder
 * @remark:
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final TokenHelper tokenHelper;

    public TokenInterceptor(TokenHelper tokenHelper) {
        this.tokenHelper = tokenHelper;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            if (method.isAnnotationPresent(TokenRequired.class)) {
                String token = request.getHeader(Constants.AUTHORIZATION);
                if (token == null || !tokenHelper.parseToken(token).isValidAccessToken()) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    throw new TokenException();
                }
            }
        }
        return true;
    }

}
