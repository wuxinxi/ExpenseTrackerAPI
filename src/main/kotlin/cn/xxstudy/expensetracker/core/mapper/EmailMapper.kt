package cn.xxstudy.expensetracker.core.mapper

import cn.xxstudy.expensetracker.data.table.Email
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * @date: 2023/7/8 19:15
 * @author: LovelyCoder
 * @remark:
 */
@Mapper
interface EmailMapper : BaseMapper<Email>