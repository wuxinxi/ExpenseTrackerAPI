package cn.xxstudy.expensetracker

import cn.xxstudy.expensetracker.utils.date.DateUtils
import org.junit.jupiter.api.Test
import java.sql.DriverManager.println
import java.time.format.DateTimeFormatter

/**
 * @date: 2023/7/2 12:27
 * @author: LovelyCoder
 * @remark:
 */

class SimpleTest {
    @Test
    fun run() {
        val date = "2023-07-25 00:00:00"
        println(DateUtils.max(date).format(DateTimeFormatter.ofPattern(DateUtils.DEFAULT_FORMAT)))

    }

}