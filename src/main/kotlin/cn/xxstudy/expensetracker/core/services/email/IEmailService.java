package cn.xxstudy.expensetracker.core.services.email;

import cn.xxstudy.expensetracker.data.table.Email;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @date: 2023/7/8 19:15
 * @author: LovelyCoder
 * @remark:
 */
public interface IEmailService extends IService<Email> {

    void generateVerifyCode(String email);

    boolean checkVerifyCode(String email, String code);
}
