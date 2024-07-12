package cn.xxstudy.expensetracker.core.controller;

import cn.xxstudy.expensetracker.annotation.Status;
import cn.xxstudy.expensetracker.data.Response;
import cn.xxstudy.expensetracker.data.model.UserJ;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


/**
 * @date: 2023/11/29 17:23
 * @author: TangRen
 * @remark:
 */
@Validated
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/go")
    public Response<String> getCategory(@Valid @NotBlank(message = "name不能为空") String name, @Valid @Status(statusType = {"1", "2"}) Integer status) {
        return Response.success(name);
    }

    @PostMapping("/addUser")
    public Response<String> addUser(@RequestBody @Valid UserJ user) {
        return Response.success("ddd");
    }

    @GetMapping("/testEmail")
    public Response<String> testEmail(@RequestParam @Email(message = "电子邮箱不合法") String email) {
        return Response.success(email);
    }

}

