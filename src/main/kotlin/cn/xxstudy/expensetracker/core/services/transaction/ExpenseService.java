package cn.xxstudy.expensetracker.core.services.transaction;

import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.core.mapper.ExpenseMapper;
import cn.xxstudy.expensetracker.data.table.TransactionExpense;
import cn.xxstudy.expensetracker.global.exception.FormatException;
import cn.xxstudy.expensetracker.utils.TokenHelper;
import cn.xxstudy.expensetracker.utils.date.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @date: 2023/7/2 9:52
 * @author: LovelyCoder
 * @remark:
 */
@Service
public class ExpenseService extends ServiceImpl<ExpenseMapper, TransactionExpense> implements IExpenseService {
    private final ExpenseMapper mapper;
    private final TokenHelper tokenHelper;

    public ExpenseService(ExpenseMapper mapper, TokenHelper tokenHelper) {
        this.mapper = mapper;
        this.tokenHelper = tokenHelper;
    }

    @Override
    public TransactionExpense recordOrUpdate(HttpServletRequest request, TransactionExpense value) {
        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        value.setUserId(id);
        if (_recordOrUpdate(value, value.getId())) {
            return mapper.selectById(value.getId());
        }
        return null;
    }

    @Override
    public void updateProperty(TransactionExpense value) {
        value.setExpenseUpdateDate(DateUtils.createDateTime());
    }

    @Override
    public List<TransactionExpense> queryList(HttpServletRequest request, int page, int size) {
        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<TransactionExpense> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransactionExpense::getUserId, id);
        Page<TransactionExpense> selectPage = mapper.selectPage(new Page<>(page, size), queryWrapper);
        return selectPage.getRecords();
    }

    @Override
    public List<TransactionExpense> queryListByDate(HttpServletRequest request, String date) {
        if (DateUtils.isValidDateTime(date)) {
            throw new FormatException();
        }
        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<TransactionExpense> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransactionExpense::getUserId, id).eq(TransactionExpense::getExpenseDate, date);
        return mapper.selectList(queryWrapper);
    }


}
