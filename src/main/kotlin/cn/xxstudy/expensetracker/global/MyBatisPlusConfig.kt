package cn.xxstudy.expensetracker.global

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @date: 2023/7/2 11:52
 * @author: LovelyCoder
 * @remark: 注册分页
 */
@Configuration
@MapperScan("cn.xxstudy.expensetracker.core.mapper")
class MyBatisPlusConfig {
    @Bean
    fun interceptor(): MybatisPlusInterceptor {
        val interceptor = MybatisPlusInterceptor()
        interceptor.addInnerInterceptor(PaginationInnerInterceptor())
        return interceptor
    }
}