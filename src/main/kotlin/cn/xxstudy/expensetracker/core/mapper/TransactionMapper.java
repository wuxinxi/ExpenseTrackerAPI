package cn.xxstudy.expensetracker.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @date: 2023/7/7 11:47
 * @author: LovelyCoder
 * @remark:
 */
public interface TransactionMapper<T> extends BaseMapper<T> {

    Double getTotal(@Param("userId") Long userId, @Param("month") String month);

    Integer getCount(@Param("userId") Long userId, @Param("month") String month);

    List<Map<String, Object>> getByCategory(@Param("userId") Long userId, @Param("month") String month);

    List<Map<String, Object>> getByMember(@Param("userId") Long userId, @Param("month") String month);
}
