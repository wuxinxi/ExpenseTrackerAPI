package cn.xxstudy.expensetracker.core.controller;

import cn.xxstudy.expensetracker.annotation.TokenRequired;
import cn.xxstudy.expensetracker.constant.HttpCode;
import cn.xxstudy.expensetracker.core.services.category.CategoryService;
import cn.xxstudy.expensetracker.data.Response;
import cn.xxstudy.expensetracker.data.table.Category;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("/category")
    @TokenRequired
    public Response<Category> addCategory(HttpServletRequest request, @RequestParam(required = false) @Valid MultipartFile icon, @RequestParam String name) {
        Category category = service.addCategory(request, icon, name);
        return Response.success(category);
    }

    @DeleteMapping("/category")
    @TokenRequired
    public Response deleteCategory(HttpServletRequest request, @RequestParam @Valid Long id) {
        boolean delete = service.deleteCategory(request, id);
        return delete ? Response.success() : Response.failed(HttpCode.ID_ERROR);
    }

    @PatchMapping("/category")
    @TokenRequired
    public Response<Category> updateCategory(HttpServletRequest request, @RequestParam(required = false) @Valid MultipartFile icon, @RequestParam String name, Long id) {
        Category response = service.updateCategory(request, icon, name, id);
        return Optional.ofNullable(response)
                .map(Response::success)
                .orElse(Response.failed(HttpCode.ID_ERROR));
    }

    @GetMapping("/category")
    @TokenRequired
    public Response<List<Category>> queryCategory(HttpServletRequest request) {
        return Response.success(service.queryCategoryList(request));
    }

}
