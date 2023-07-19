package cn.xxstudy.expensetracker.data.table;

import lombok.Data;

/**
 * @date: 2023/7/15 22:30
 * @author: LovelyCoder
 * @remark:
 */
@Data
public class Transaction {
    private Long id;
    private Long userId;
    private double amount;
    private Long categoryId;
    private String date;
    private String createDate;
    private String updateDate;
    private Long memberId;
    private int transactionMethod;
    private int transactionType;
}
