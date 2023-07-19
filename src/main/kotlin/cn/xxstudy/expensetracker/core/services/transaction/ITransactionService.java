package cn.xxstudy.expensetracker.core.services.transaction;

import cn.xxstudy.expensetracker.data.model.TransactionSummary;
import cn.xxstudy.expensetracker.data.table.Transaction;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @date: 2023/7/2 10:06
 * @author: LovelyCoder
 * @remark:
 */
public interface ITransactionService extends IService<Transaction> {
    Transaction recordOrUpdate(HttpServletRequest request, Transaction value);

    void updateProperty(Transaction value);

    TransactionSummary queryListByMonthYear(HttpServletRequest request, String monthYear);

    default boolean _recordOrUpdate(Transaction value, Long id) {
        boolean result;
        if (!Objects.isNull(this.getById(id))) {
            updateProperty(value);
            result = updateById(value);
        } else {
            result = save(value);
        }
        return result;
    }
}
