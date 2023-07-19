package cn.xxstudy.expensetracker.core.services.transaction;

import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.core.mapper.TransactionCompatMapper;
import cn.xxstudy.expensetracker.data.model.TransactionSummary;
import cn.xxstudy.expensetracker.data.table.Transaction;
import cn.xxstudy.expensetracker.global.exception.FormatException;
import cn.xxstudy.expensetracker.utils.TokenHelper;
import cn.xxstudy.expensetracker.utils.date.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.YearMonth;
import java.util.List;

/**
 * @date: 2023/7/15 22:35
 * @author: LovelyCoder
 * @remark:
 */
@Service
public class TransactionService extends ServiceImpl<TransactionCompatMapper, Transaction> implements ITransactionService {
    final TransactionCompatMapper mapper;

    private final TokenHelper tokenHelper;

    public TransactionService(TransactionCompatMapper mapper, TokenHelper tokenHelper) {
        this.mapper = mapper;
        this.tokenHelper = tokenHelper;
    }


    @Override
    public Transaction recordOrUpdate(HttpServletRequest request, Transaction value) {
        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        value.setUserId(id);
        if (_recordOrUpdate(value, value.getId())) {
            return mapper.selectById(value.getId());
        }
        return null;
    }

    @Override
    public void updateProperty(Transaction value) {
        value.setUpdateDate(DateUtils.createDateTime());
    }

    @Override
    public TransactionSummary queryListByMonthYear(HttpServletRequest request, String monthYear) {
        if (DateUtils.isValidMonthTime(monthYear)) {
            throw new FormatException();
        }
        YearMonth yearMonth = YearMonth.parse(monthYear);
        String start = String.format("%s-01", monthYear);
        String end = yearMonth.atEndOfMonth().toString();

        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<Transaction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Transaction::getUserId, id).between(Transaction::getDate, start, end);
        List<Transaction> transactions = mapper.selectList(queryWrapper);
        Double incomeTotal = mapper.getTotal(id, Constants.INCOME, monthYear);
        Double expenseTotal = mapper.getTotal(id, Constants.EXPENSE, monthYear);
        int count = mapper.getCount(id, monthYear);
        return new TransactionSummary(transactions, incomeTotal != null ? incomeTotal : 0.0, expenseTotal != null ? expenseTotal : 0.0, count);
    }


}
