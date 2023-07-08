package cn.xxstudy.expensetracker.core.services.analysis;

import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.core.mapper.TransactionMapper;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @date: 2023/7/7 9:27
 * @author: LovelyCoder
 * @remark:
 */

public interface IAnalysisService<T> extends IService<T> {
    Map<String, Object> analysis(HttpServletRequest request, String month);

    default Map<String, Object> analysis(TransactionMapper<T> mapper, Long id, String month) {
        Map<String, Object> map = new HashMap<>();
        //1. 总收入
        Double total = Optional.ofNullable(mapper.getTotal(id, month)).orElse(0.0);
        map.put(Constants.TOTAL, total);
        //2. 记账笔数
        Integer incomeCount = Optional.ofNullable(mapper.getCount(id, month)).orElse(0);
        map.put(Constants.COUNT, incomeCount);
        //3. 分类占比
        List<Map<String, Object>> byCategory = mapper.getByCategory(id, month);
        calculatePercentage(total, byCategory);
        map.put(Constants.BY_CATEGORY, byCategory);
        //4. 成员占比
        List<Map<String, Object>> byMember = mapper.getByMember(id, month);
        calculatePercentage(total, byMember);
        map.put(Constants.BY_MEMBER, byMember);
        return map;
    }

    default void calculatePercentage(double totalCount, List<Map<String, Object>> mapList) {
        for (Map<String, Object> map : mapList) {
            if (totalCount == 0) {
                map.put(Constants.PROPORTION, 0);
                return;
            }
            Object totalObj = Optional.ofNullable(map.get(Constants.TOTAL)).orElse(0.0);
            double total = (double) totalObj;
            BigDecimal bd = new BigDecimal(total / totalCount);
            double proportion = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
            map.put(Constants.PROPORTION, proportion);
        }
    }
}
