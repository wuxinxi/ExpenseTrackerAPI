package cn.xxstudy.expensetracker.data.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @date: 2023/7/2 9:23
 * @author: LovelyCoder
 * @remark:
 */
@Data
@JsonIgnoreProperties({"userId"})
public class Category {
    private Long id;
    private Long userId;
    private String categoryName;
    private String categoryIconUrl;
}
