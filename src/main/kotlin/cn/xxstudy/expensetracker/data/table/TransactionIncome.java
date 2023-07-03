package cn.xxstudy.expensetracker.data.table;

import lombok.Data;

@Data
public class TransactionIncome {
    private Long id;
    private Long userId;
    private double incomeAmount;
    private Long categoryId;
    private String incomeCreateDate;
    private String incomeUpdateDate;
    private Long memberId;
    private int incomeType;
}
