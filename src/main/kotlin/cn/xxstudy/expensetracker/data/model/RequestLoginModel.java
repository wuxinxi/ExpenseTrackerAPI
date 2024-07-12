package cn.xxstudy.expensetracker.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestLoginModel {
    @NotBlank(message = "邮箱不能为空")
//    @Email(message = "邮箱格式错误")
    private String userEmail;

    @NotBlank(message = "密码不能为空")
    private String userPassword;

}
