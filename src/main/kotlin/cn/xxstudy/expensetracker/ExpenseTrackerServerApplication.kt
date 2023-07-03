package cn.xxstudy.expensetracker

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("cn.xxstudy.expensetracker.core.mapper")
class ExpenseTrackerServerApplication

fun main(args: Array<String>) {
    runApplication<ExpenseTrackerServerApplication>(*args)
}
