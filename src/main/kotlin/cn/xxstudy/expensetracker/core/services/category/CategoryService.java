package cn.xxstudy.expensetracker.core.services.category;

import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.core.mapper.CategoryMapper;
import cn.xxstudy.expensetracker.data.table.Category;
import cn.xxstudy.expensetracker.utils.TokenHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @date: 2023/7/4 14:15
 * @author: TangRen
 * @remark:
 */
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    private final CategoryMapper categoryMapper;
    private final TokenHelper tokenHelper;

    public CategoryService(CategoryMapper categoryMapper, TokenHelper tokenHelper) {
        this.categoryMapper = categoryMapper;
        this.tokenHelper = tokenHelper;
    }

    @SneakyThrows
    @Override
    public Category addCategory(HttpServletRequest request, MultipartFile icon, @RequestParam String name) {

        String f = request.getSession().getServletContext()
                .getRealPath("/UploadFiles/db/photo/");

        File uploadDir = new File(f);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }



        Path filePath = Paths.get(uploadDir.getAbsolutePath(), icon.getOriginalFilename());
        Files.write(filePath, icon.getBytes());


        Long userId = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
//        ImageUploadHelper.upload(icon, userId.toString(), 100_000, 300);

        Category category = new Category();
        category.setUserId(userId);
        category.setCategoryName(name);

        String baseUrl = request.getRequestURL().toString();
        String imageUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/")) + "/upload/" + icon.getOriginalFilename();
        category.setCategoryIconUrl(f);
        categoryMapper.insert(category);
        return category;
    }

    @Override
    public boolean deleteCategory(HttpServletRequest request, Category category) {
        removeById(category.getId());
        return true;
    }

    @Override
    public List<Category> queryCategoryList(HttpServletRequest request) {
        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getUserId, id);
        return categoryMapper.selectList(queryWrapper);
    }
}
