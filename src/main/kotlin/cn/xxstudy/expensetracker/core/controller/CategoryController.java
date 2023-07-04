package cn.xxstudy.expensetracker.core.controller;

import cn.xxstudy.expensetracker.core.services.category.CategoryService;
import cn.xxstudy.expensetracker.data.Response;
import cn.xxstudy.expensetracker.data.table.Category;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @date: 2023/7/4 14:47
 * @author: TangRen
 * @remark:
 */
@RestController
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/addCategory")
    public Response<Category> addCategory(HttpServletRequest request, @RequestParam @Valid MultipartFile icon, @RequestParam String name) {
        Category category = service.addCategory(request, icon, name);
        return Response.success(category);
    }

}
