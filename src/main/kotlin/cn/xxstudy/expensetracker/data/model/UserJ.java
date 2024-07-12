package cn.xxstudy.expensetracker.data.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @date: 2023/11/30 12:10
 * @author: TangRen
 * @remark:
 */
@Data
public class UserJ {
    @NotBlank(message = "名称必填")
    private String userName;
    @NotBlank(message = "密码必填")
    private String userSecret;
    @NotBlank(message = "邮箱必填")
    @Email(message = "邮箱格式错误")
    private String email;

}
