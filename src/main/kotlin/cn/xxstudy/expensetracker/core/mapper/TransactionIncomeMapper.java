package cn.xxstudy.expensetracker.core.mapper;

import cn.xxstudy.expensetracker.data.table.TransactionIncome;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @date: 2023/7/6 21:10
 * @author: LovelyCoder
 * @remark:
 */
@Mapper
public interface TransactionIncomeMapper extends TransactionMapper<TransactionIncome> {
    @Override
    @Select("SELECT SUM(income_amount) FROM t_transaction_income WHERE user_id=#{userId} AND DATE_FORMAT(income_date,'%Y-%m')=#{month}")
    Double getTotal(@Param("userId") Long userId, @Param("month") String month);

    @Override
    @Select("SELECT COUNT(*) FROM t_transaction_income WHERE user_id=#{userId} AND DATE_FORMAT(income_date,'%Y-%m')=#{month}")
    Integer getCount(@Param("userId") Long userId, @Param("month") String month);

    @Override
    @Select("SELECT c.id, c.category_name, c.category_icon_url, SUM(i.income_amount) AS total FROM t_transaction_income i LEFT JOIN t_category c ON i.category_id=c.id WHERE i.user_id=#{userId} AND DATE_FORMAT(i.income_date,'%Y-%m')=#{month} GROUP BY i.category_id")
    List<Map<String, Object>> getByCategory(@Param("userId") Long userId, @Param("month") String month);

    @Override
    @Select("SELECT c.id, c.member_name, c.member_avatar_url, SUM(i.income_amount) AS total FROM t_transaction_income i LEFT JOIN t_member c ON i.member_id=c.id WHERE i.user_id=#{userId} AND DATE_FORMAT(i.income_date,'%Y-%m')=#{month} GROUP BY i.member_id")
    List<Map<String, Object>> getByMember(@Param("userId") Long userId, @Param("month") String month);
}
