package cn.xxstudy.expensetracker.data.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RequestLoginModel {
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String userEmail;

    @NotBlank(message = "密码不能为空")
    private String userPassword;

}
