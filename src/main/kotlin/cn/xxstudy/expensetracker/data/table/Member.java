package cn.xxstudy.expensetracker.data.table;

import lombok.Data;

/**
 * @date: 2023/7/2 9:23
 * @author: LovelyCoder
 * @remark:
 */
@Data
public class Member {
    private Long id;
    private Long userId;
    private String memberName;
    private String memberAvatarUrl;
}
