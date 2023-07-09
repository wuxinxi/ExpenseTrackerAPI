package cn.xxstudy.expensetracker.core.services.category;

import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.constant.ErrorCode;
import cn.xxstudy.expensetracker.core.mapper.CategoryMapper;
import cn.xxstudy.expensetracker.data.table.Category;
import cn.xxstudy.expensetracker.global.exception.AppException;
import cn.xxstudy.expensetracker.utils.ImageUploadHelper;
import cn.xxstudy.expensetracker.utils.TokenHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
        Long userId = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getUserId, userId).eq(Category::getCategoryName, name);
        if (categoryMapper.exists(queryWrapper)) {
            throw new AppException(ErrorCode.CATEGORY_ERROR);
        }
        String imageUrl = ImageUploadHelper.upload(request, icon, userId.toString(), 100_000, 300);
        Category category = Category.builder()
                .userId(userId)
                .categoryName(name)
                .categoryIconUrl(imageUrl)
                .build();
        categoryMapper.insert(category);
        return category;
    }

    @Override
    public boolean deleteCategory(HttpServletRequest request, Long id) {
        return removeById(id);
    }

    @SneakyThrows
    @Override
    @Nullable
    public Category updateCategory(HttpServletRequest request, MultipartFile icon, String name, Long id) {
        Long userId = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getId, id);
        Category category = categoryMapper.selectOne(queryWrapper);
        if (category == null) return null;
        String oldImageUrl = category.getCategoryIconUrl();
        String newImageUrl = ImageUploadHelper.upload(request, icon, userId.toString(), 100_000, 300);
        if (newImageUrl != null) {
            category.setCategoryIconUrl(newImageUrl);
        }
        category.setCategoryName(name);
        boolean update = updateById(category);
        if (update) {
            ImageUploadHelper.deleteOldImage(userId.toString(), oldImageUrl, newImageUrl);
        }
        return update ? category : null;
    }

    @Override
    public List<Category> queryCategoryList(HttpServletRequest request) {
        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getUserId, id);
        return categoryMapper.selectList(queryWrapper);
    }
}
