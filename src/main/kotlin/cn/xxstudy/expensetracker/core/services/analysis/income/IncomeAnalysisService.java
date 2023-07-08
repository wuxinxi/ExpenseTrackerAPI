package cn.xxstudy.expensetracker.core.services.analysis.income;

import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.core.mapper.TransactionIncomeMapper;
import cn.xxstudy.expensetracker.core.services.analysis.IAnalysisService;
import cn.xxstudy.expensetracker.data.table.TransactionIncome;
import cn.xxstudy.expensetracker.utils.TokenHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @date: 2023/7/7 9:23
 * @author: LovelyCoder
 * @remark:
 */
@Service
public class IncomeAnalysisService extends ServiceImpl<TransactionIncomeMapper, TransactionIncome> implements IAnalysisService<TransactionIncome> {
    private final TransactionIncomeMapper mapper;
    private final TokenHelper tokenHelper;

    public IncomeAnalysisService(TransactionIncomeMapper mapper, TokenHelper tokenHelper) {
        this.mapper = mapper;
        this.tokenHelper = tokenHelper;
    }

    @Override
    public Map<String, Object> analysis(HttpServletRequest request, String month) {
        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        return analysis(mapper, id, month);
    }

}
