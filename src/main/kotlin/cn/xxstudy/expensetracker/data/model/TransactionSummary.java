package cn.xxstudy.expensetracker.data.model;

import cn.xxstudy.expensetracker.data.table.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @date: 2023/7/15 23:02
 * @author: LovelyCoder
 * @remark:
 */
@Data
@AllArgsConstructor
public class TransactionSummary {
    private List<Transaction> transactions;
    private double incomeTotal;
    private double expenseTotal;
    private int count;
}
