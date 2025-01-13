package com.mdd.ela.service.base;

import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author dungmd
 * @created 1/12/2025 5:55 下午
 * @project e-learning-app
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BaseMailServiceImpl implements BaseMailService {
    JavaMailSender mailSender;

    public BaseMailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOtpEmail(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("ela.contact.vn@gmail.com");
            helper.setTo(email);
            helper.setSubject("Xác minh tài khoản email của bạn");
            String htmlContent = "<html><body>"
                    + "<p style='font-family: Arial, sans-serif; font-size: 18px;'>"
                    + "OTP của bạn là: <b>" + otp + "</b>.</p>"
                    + "<p style='font-family: Arial, sans-serif; font-size: 14px;'>"
                    + "Ela sẽ không bao giờ gửi email cho bạn và yêu cầu bạn tiết lộ hoặc xác minh mật khẩu, thẻ tín dụng hoặc số tài khoản ngân hàng của bạn.</p>"
                    + "<p style='font-family: Arial, sans-serif; font-size: 14px;'>"
                    + "Nếu bạn nhận được một email đáng ngờ có liên kết cập nhật thông tin tài khoản của mình, đừng nhấp vào liên kết."
                    + "</p>"
                    + "<p style='font-family: Arial, sans-serif; font-size: 14px;'>"
                    + "Thay vào đó, hãy báo cáo email đó cho Ela để điều tra.</p>"
                    + "</body></html>";
            helper.setText(htmlContent, true);

            mailSender.send(message); // Gửi email
        } catch (Exception e) {
            throw new RuntimeException("Không thể gửi OTP tới email của bạn", e);
        }
    }
}
