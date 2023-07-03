package cn.xxstudy.expensetracker.core.services.transaction;

import cn.xxstudy.expensetracker.utils.date.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * @date: 2023/7/2 10:06
 * @author: LovelyCoder
 * @remark:
 */
public interface ITransaction<T> extends IService<T> {
    T recordOrUpdate(HttpServletRequest request, T value);

    void updateProperty(T value);

    List<T> queryList(HttpServletRequest request,int page, int size);

    List<T> queryListByDate(HttpServletRequest request,String date);

    default boolean _recordOrUpdate(T value, Long id) {
        boolean result;
        if (!Objects.isNull(this.getById(id))) {
            updateProperty(value);
            result = updateById(value);
        } else {
            result = save(value);
        }
        return result;
    }

    default LambdaQueryWrapper<T> createQueryWrapper(String date, SFunction<T, ?> userIdColumn, Long id, SFunction<T, ?> createDateColumn) {
        LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
        String startTime = DateUtils.min(date).format(DateTimeFormatter.ofPattern(DateUtils.DEFAULT_FORMAT));
        String endTime = DateUtils.max(date).format(DateTimeFormatter.ofPattern(DateUtils.DEFAULT_FORMAT));
        queryWrapper
                .eq(userIdColumn, id)
                .between(createDateColumn, startTime, endTime);
        return queryWrapper;
    }

}
