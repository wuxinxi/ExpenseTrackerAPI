package cn.xxstudy.expensetracker.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * @date: 2023/11/30 10:20
 * @author: TangRen
 * @remark:
 */
public class StatusValidator implements ConstraintValidator<Status, Integer> {

    private List<String> typeStatus;

    @Override
    public void initialize(Status constraintAnnotation) {
        typeStatus = Arrays.asList(constraintAnnotation.statusType());
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null) {
            return typeStatus.contains(String.valueOf(value));
        }
        return false;
    }
}
