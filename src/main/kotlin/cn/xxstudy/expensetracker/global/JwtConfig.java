package cn.xxstudy.expensetracker.global;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @date: 2023/7/3 20:07
 * @author: LovelyCoder
 * @remark:
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
    private String secret;
    private long accessExpiration;
    private long refreshExpiration;

    public long getAccessExpiration() {
        return accessExpiration * 1000;
    }

    public long getRefreshExpiration() {
        return refreshExpiration * 1000;
    }
}
