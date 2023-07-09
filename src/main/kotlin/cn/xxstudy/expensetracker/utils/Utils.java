package cn.xxstudy.expensetracker.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @date: 2023/7/8 19:25
 * @author: LovelyCoder
 * @remark:
 */
public class Utils {
    public static boolean isValidEmail(String email) {
        // 邮箱格式使用正则表达式进行校验
        Pattern pattern = Pattern.compile("^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
