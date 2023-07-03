package cn.xxstudy.expensetracker

import cn.xxstudy.expensetracker.core.mapper.UserMapper
import cn.xxstudy.expensetracker.data.table.User
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.sql.DriverManager.println
import javax.annotation.Resource

@SpringBootTest
class ExpenseTrackerServerApplicationTests {

    @Resource
    private lateinit var userMapper: UserMapper

    @Test
    fun contextLoads() {
        val queryWrapper = LambdaQueryWrapper<User>()
        queryWrapper.eq(User::getUserEmail, "996489865@qq.com")
        val selectOne = userMapper.selectList(queryWrapper)
        println("ExpenseTrackerServerApplicationTests.contextLoads selectOne=$selectOne")
        val user = userMapper.selectList(null)

        user.forEach {
           println(it.toString())
        }
    }

}
