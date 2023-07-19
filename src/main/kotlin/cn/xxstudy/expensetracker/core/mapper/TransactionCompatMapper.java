package cn.xxstudy.expensetracker.core.mapper;

import cn.xxstudy.expensetracker.data.table.Transaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @date: 2023/7/7 11:47
 * @author: LovelyCoder
 * @remark:
 */
public interface TransactionCompatMapper extends BaseMapper<Transaction> {

    /**
     * 当前月总收入
     *
     * @param userId
     * @param month
     * @return
     */
    @Select("SELECT SUM(amount) FROM t_transaction WHERE user_id=#{userId} AND transaction_type=#{transactionType} AND DATE_FORMAT(date,'%Y-%m')=#{month}")
    Double getTotal(@Param("userId") Long userId, @Param("transactionType") int transactionType, @Param("month") String month);

    /**
     * 当前月总笔数
     *
     * @param userId
     * @param month
     * @return
     */
    @Select("SELECT COUNT(*) FROM t_transaction WHERE user_id=#{userId} AND DATE_FORMAT(date,'%Y-%m')=#{month}")
    Integer getCount(@Param("userId") Long userId, @Param("month") String month);

}
