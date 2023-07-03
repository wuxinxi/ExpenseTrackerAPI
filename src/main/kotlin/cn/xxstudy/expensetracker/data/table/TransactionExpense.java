package cn.xxstudy.expensetracker.data.table;

import lombok.Data;

@Data
public class TransactionExpense {
    private Long id;
    private Long userId;
    private double expenseAmount;
    private Long categoryId;
    private String expenseCreateDate;
    private String expenseUpdateDate;
    private Long memberId;
    private int expenseType;
}
