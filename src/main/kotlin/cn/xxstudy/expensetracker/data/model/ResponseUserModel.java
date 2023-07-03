package cn.xxstudy.expensetracker.data.model;

import cn.xxstudy.expensetracker.data.table.User;
import lombok.Data;

/**
 * @date: 2023/7/3 20:39
 * @author: LovelyCoder
 * @remark:
 */
@Data
public class ResponseUserModel {
    private String userName;
    private String userEmail;
    private String userCreateDate;
    private String userAvatarUrl;
    private String userPhone;
    private int userGender;
    private Token token;

    public ResponseUserModel(User user){
        userName=user.getUserName();
        userEmail=user.getUserEmail();
        userCreateDate=user.getUserCreateDate();
        userAvatarUrl=user.getUserAvatarUrl();
        userPhone=user.getUserPhone();
        userGender=user.getUserGender();
    }

}
