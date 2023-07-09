package cn.xxstudy.expensetracker.data.table;

import lombok.Data;

/**
 * @date: 2023/7/8 19:13
 * @author: LovelyCoder
 * @remark:
 */
@Data
public class Email {
    private Long id;
    private String email;
    private String date;
    private String verifyCode;
}
