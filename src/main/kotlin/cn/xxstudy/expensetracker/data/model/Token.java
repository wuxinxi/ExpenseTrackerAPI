package cn.xxstudy.expensetracker.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @date: 2023/7/3 20:29
 * @author: LovelyCoder
 * @remark:
 */
@Data
@AllArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;
}
