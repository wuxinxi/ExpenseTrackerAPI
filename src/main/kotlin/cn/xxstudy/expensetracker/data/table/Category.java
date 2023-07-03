package cn.xxstudy.expensetracker.data.table;

import lombok.Data;

/**
 * @date: 2023/7/2 9:23
 * @author: LovelyCoder
 * @remark:
 */
@Data
public class Category {
    private Long id;
    private Long userId;
    private String categoryName;
    private String categoryIconUrl;
}
