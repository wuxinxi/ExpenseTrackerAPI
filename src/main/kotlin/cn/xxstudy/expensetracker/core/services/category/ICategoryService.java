package cn.xxstudy.expensetracker.core.services.category;

import cn.xxstudy.expensetracker.data.table.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @date: 2023/7/4 14:15
 * @author: TangRen
 * @remark:
 */
public interface ICategoryService extends IService<Category> {
    Category addCategory(HttpServletRequest request, MultipartFile icon, String name) throws IOException;

    boolean deleteCategory(HttpServletRequest request, Long id);

    Category updateCategory(HttpServletRequest request, MultipartFile icon, String name, Long id);

    List<Category> queryCategoryList(HttpServletRequest request);
}
