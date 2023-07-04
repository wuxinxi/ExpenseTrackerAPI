package cn.xxstudy.expensetracker.utils;

import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.data.bean.TokenData;
import cn.xxstudy.expensetracker.global.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @date: 2023/7/2 21:46
 * @author: LovelyCoder
 * @remark:
 */
@Component
public class TokenHelper {
    private final JwtConfig config;

    public TokenHelper(JwtConfig config) {
        this.config = config;
    }

    public String generateRefreshToken(Long id) {
        return generateToken(id, config.getRefreshExpiration(), TokenType.REFRESH_TOKEN);
    }

    public String generateAccessToken(Long id) {
        return generateToken(id, config.getAccessExpiration(), TokenType.ACCESS_TOKEN);
    }

    public String generateToken(Long id, long expiration, TokenType type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.RANDOM, new Random().nextLong());
        claims.put(Constants.ID, id);
        claims.put(Constants.TYPE, type);
        Date nowDate = new Date();
        Date expirationDate = new Date(nowDate.getTime() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("cn.xxstudy.api")
                .setIssuedAt(nowDate)
                .setExpiration(expirationDate)
                .setSubject(id.toString())
                .signWith(SignatureAlgorithm.HS256, config.getSecret())
                .compact();
    }


    public TokenData parseToken(String token) {
        Claims body = Jwts.parser().setSigningKey(config.getSecret())
                .parseClaimsJws(token)
                .getBody();
        Long id = Long.parseLong(body.getSubject());
        Date expiration = body.getExpiration();
        Object type = body.get(Constants.TYPE);
        return TokenData.builder()
                .tokenType(TokenType.ACCESS_TOKEN.name().equals(type) ? TokenType.ACCESS_TOKEN : TokenType.REFRESH_TOKEN)
                .id(id)
                .expiration(expiration.before(new Date()))
                .build();
    }


    public Long extractUserId(String token) {
        return Long.parseLong(Jwts.parser()
                .setSigningKey(config.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }


}
