package cn.xxstudy.expensetracker.core.services.transaction;

import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
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

    //当天yyyy-MM-dd
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
}
