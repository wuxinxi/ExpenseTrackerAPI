package cn.xxstudy.expensetracker.core.services.email;

import cn.xxstudy.expensetracker.core.mapper.EmailMapper;
import cn.xxstudy.expensetracker.data.table.Email;
import cn.xxstudy.expensetracker.global.exception.AppException;
import cn.xxstudy.expensetracker.utils.Utils;
import cn.xxstudy.expensetracker.utils.date.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * @date: 2023/7/8 19:18
 * @author: LovelyCoder
 * @remark:
 */
@Service
public class EmailService extends ServiceImpl<EmailMapper, Email> implements IEmailService {
    private final JavaMailSender emailSender;
    private final EmailMapper emailMapper;

    public EmailService(JavaMailSender emailSender, EmailMapper emailMapper) {
        this.emailSender = emailSender;
        this.emailMapper = emailMapper;
    }

    @Override
    public void generateVerifyCode(String email) {
        if (!Utils.isValidEmail(email)) {
            throw new AppException(-1, "无效的邮箱地址");
        }

        LambdaQueryWrapper<Email> queryWrapper = new LambdaQueryWrapper<>();
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(5);
        queryWrapper.eq(Email::getEmail, email).ge(Email::getDate, startTime);
        List<Email> emails = emailMapper.selectList(queryWrapper);
        if (emails.size() >= 2) {
            throw new AppException(-1, "您的操作过于频繁，请稍后再试");
        }

        int code = new Random().nextInt(999999);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("【记YI笔】邮箱验证码");
        message.setText(String.format("您好，本次的邮箱验证码是 %s , 验证码五分钟内有效, 请及时填写", code));
        message.setTo(email);
        message.setFrom("996489865@qq.com");
        emailSender.send(message);
        Email mail = new Email();
        mail.setEmail(email);
        mail.setVerifyCode(String.valueOf(code));
        save(mail);
    }

    @Override
    public boolean checkVerifyCode(String email, String code) {
        LambdaQueryWrapper<Email> queryWrapper = new LambdaQueryWrapper<>();
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(5);

        queryWrapper.eq(Email::getEmail, email)
                .ge(Email::getDate, DateUtils.convertDateTime(startTime))
                .orderByDesc(Email::getDate)
                .last("limit 1");
        Email mail = emailMapper.selectOne(queryWrapper);
        return mail != null && StringUtils.equals(mail.getVerifyCode(), code);
    }
}
