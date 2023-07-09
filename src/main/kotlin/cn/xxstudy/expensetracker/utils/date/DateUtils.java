package cn.xxstudy.expensetracker.utils.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * @date: 2023/7/2 11:12
 * @author: LovelyCoder
 * @remark:
 */
public class DateUtils {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String createDateTime() {
        return createDateTime(DEFAULT_FORMAT);
    }

    public static String createDateTime(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(now);
    }

    public static LocalDateTime convertStartOfDay(String dateValue) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate date = LocalDate.parse(dateValue, formatter);
        return date.atStartOfDay();
    }

    public static LocalDateTime min(String date) {
        return min(date, DEFAULT_FORMAT);
    }

    public static LocalDateTime min(String date, String format) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    public static LocalDateTime max(String date) {
        return max(date, DEFAULT_FORMAT);
    }

    public static LocalDateTime max(String date, String format) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }

    public static String convertDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_FORMAT));
    }

    public static boolean isValidDateTime(String dateTimeString) {
        String pattern = "^\\d{4}-\\d{2}-\\d{2}$";
        return !Pattern.matches(pattern, dateTimeString);
    }

}

