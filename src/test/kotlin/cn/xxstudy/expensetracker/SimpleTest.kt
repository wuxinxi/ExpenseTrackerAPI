package cn.xxstudy.expensetracker

import cn.xxstudy.expensetracker.utils.date.DateUtils
import org.junit.jupiter.api.Test
import java.sql.DriverManager.println
import java.time.YearMonth
import java.time.format.DateTimeFormatter


/**
 * @date: 2023/7/2 12:27
 * @author: LovelyCoder
 * @remark:
 */

class SimpleTest {
    @Test
    fun run() {
        val monthYear  = "2023-07"
        val yearMonth = YearMonth.parse(monthYear)
        val lastDay = yearMonth.atEndOfMonth()
        System.out.println("Last day of " + monthYear  + " is " + lastDay);

    }

}