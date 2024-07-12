package cn.xxstudy.expensetracker.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @date: 2023/11/30 10:18
 * @author: TangRen
 * @remark:
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {StatusValidator.class})
public @interface Status {
    String[] statusType() default {};

    String message() default "状态错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
