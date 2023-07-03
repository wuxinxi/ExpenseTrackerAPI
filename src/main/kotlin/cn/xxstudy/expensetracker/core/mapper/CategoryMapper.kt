package cn.xxstudy.expensetracker.core.mapper

import cn.xxstudy.expensetracker.data.table.Category
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * @date: 2023/7/2 9:32
 * @author: LovelyCoder
 * @remark:
 */
@Mapper
interface CategoryMapper : BaseMapper<Category>