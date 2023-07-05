package cn.xxstudy.expensetracker.core.services.transaction;

import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.core.mapper.IncomeMapper;
import cn.xxstudy.expensetracker.data.table.TransactionIncome;
import cn.xxstudy.expensetracker.global.exception.FormatException;
import cn.xxstudy.expensetracker.utils.TokenHelper;
import cn.xxstudy.expensetracker.utils.date.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

/**
 * @date: 2023/7/2 9:52
 * @author: LovelyCoder
 * @remark:
 */

@Service
public class IncomeService extends ServiceImpl<IncomeMapper, TransactionIncome> implements IIncomeService {
    private final IncomeMapper mapper;
    private final TokenHelper tokenHelper;

    public IncomeService(IncomeMapper mapper, TokenHelper tokenHelper) {
        this.mapper = mapper;
        this.tokenHelper = tokenHelper;
    }

    @Override
    public TransactionIncome recordOrUpdate(HttpServletRequest request, TransactionIncome value) {
        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        value.setUserId(id);
        if (_recordOrUpdate(value, value.getId())) {
            return mapper.selectById(value.getId());
        }
        return null;
    }

    @Override
    public void updateProperty(TransactionIncome value) {
        value.setIncomeUpdateDate(DateUtils.createDateTime());
    }

    @Override
    public List<TransactionIncome> queryList(HttpServletRequest request, int page, int size) {
        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<TransactionIncome> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransactionIncome::getUserId, id);
        Page<TransactionIncome> selectPage = mapper.selectPage(new Page<>(page, size), queryWrapper);
        return selectPage.getRecords();
    }

    @Override
    public List<TransactionIncome> queryListByDate(HttpServletRequest request, String date) {
        if (!DateUtils.isValidDateTime(date)) {
            throw new FormatException();
        }
        LocalDate dateTime = LocalDate.of(2023, 7, 4);
        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<TransactionIncome> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransactionIncome::getUserId, id).eq(TransactionIncome::getIncomeDate, dateTime);
        return mapper.selectList(queryWrapper);
    }
}
