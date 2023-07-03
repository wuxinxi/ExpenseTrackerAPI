package cn.xxstudy.expensetracker.utils;

import cn.xxstudy.expensetracker.global.JwtConfig;
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
        return generateToken(id, config.getRefreshExpiration());
    }

    public String generateAccessToken(Long id) {
        return generateToken(id, config.getAccessExpiration());
    }

    public String generateToken(Long id, long expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("random", new Random().nextLong());
        claims.put("id", id);
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

    public boolean validateToken(Long id, String token) {
        return id.equals(extractUserId(token)) && !isTokenExpired(token);
    }

    public Long extractUserId(String token) {
        return Long.parseLong(Jwts.parser()
                .setSigningKey(config.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(config.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }


}
