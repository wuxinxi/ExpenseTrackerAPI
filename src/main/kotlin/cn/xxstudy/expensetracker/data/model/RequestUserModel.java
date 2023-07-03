package cn.xxstudy.expensetracker.data.model;

import cn.xxstudy.expensetracker.data.table.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @date: 2023/7/3 20:25
 * @author: LovelyCoder
 * @remark:
 */
@Data
public class RequestUserModel {
    @NotBlank(message = "用户名不能为空")
    @Length(message = "用户名最大长度为20", max = 20)
    private String userName;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String userEmail;
    @NotBlank(message = "密码不能为空")
    private String userPassword;
    private String userAvatarUrl;
    private String userPhone;
    private int userGender;

    public User toUser() {
        return User
                .builder()
                .userEmail(userEmail)
                .userPassword(userPassword)
                .userName(userName)
                .userPhone(userPhone)
                .userGender(userGender)
                .userAvatarUrl(userAvatarUrl)
                .build();
    }
}
