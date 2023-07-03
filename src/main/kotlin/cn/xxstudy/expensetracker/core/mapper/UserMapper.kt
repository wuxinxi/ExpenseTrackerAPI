package cn.xxstudy.expensetracker.core.mapper

import cn.xxstudy.expensetracker.data.table.User
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper : BaseMapper<User>