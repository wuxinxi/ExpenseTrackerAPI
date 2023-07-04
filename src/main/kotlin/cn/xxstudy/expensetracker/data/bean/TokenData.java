package cn.xxstudy.expensetracker.data.bean;

import cn.xxstudy.expensetracker.utils.TokenType;
import lombok.Builder;
import lombok.Data;

/**
 * @date: 2023/7/4 10:58
 * @author: TangRen
 * @remark:
 */
@Data
@Builder
public class TokenData {
    private Long id;
    private TokenType tokenType;
    private boolean expiration;

    public boolean isValidAccessToken() {
        return tokenType == TokenType.ACCESS_TOKEN && !expiration;
    }

    public boolean isValidRefreshToken() {
        return tokenType == TokenType.REFRESH_TOKEN && !expiration;
    }

}
