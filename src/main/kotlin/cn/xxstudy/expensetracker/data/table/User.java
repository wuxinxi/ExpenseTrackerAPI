package cn.xxstudy.expensetracker.data.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 常用参数注解
 * 注解	功能
 *
 * @AssertFalse 可以为null, 如果不为null的话必须为false
 * @AssertTrue 可以为null, 如果不为null的话必须为true
 * @DecimalMax 设置不能超过最大值
 * @DecimalMin 设置不能超过最小值
 * @Digits 设置必须是数字且数字整数的位数和小数的位数必须在指定范围内
 * @Future 日期必须在当前日期的未来
 * @Past 日期必须在当前日期的过去
 * @Max 最大不得超过此最大值
 * @Min 最大不得小于此最小值
 * @NotNull 不能为null，可以是空
 * @Null 必须为null
 * @Pattern 必须满足指定的正则表达式
 * @Size 集合、数组、map等的size()值必须在指定范围内
 * @Email 必须是email格式
 * @Length 长度必须在指定范围内
 * @NotBlank 字符串不能为null, 字符串trim()后也不能等于“”
 * @NotEmpty 不能为null，集合、数组、map等size()不能为0；字符串trim()后可以等于“”
 * @Range 值必须在指定范围内
 * @URL 必须是一个URL
 * </p>
 */
@JsonIgnoreProperties({"id", "userPassword"})
@Data
@Builder
public class User {
    private Long id;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userCreateDate;
    private String userAvatarUrl;
    private String userPhone;
    private int userGender;

}
