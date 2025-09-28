package kg.nail.microgram.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kg.nail.microgram.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String myEmail;

    @Override
    public void sendVerificationEmail(String email, String verificationCode) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(myEmail);
            helper.setTo(email);
            helper.setSubject("Verify your email");
            helper.setText("<h2>Verify your email</h2>" +
                            "<p>Click the link below to verify your email address:</p>" +
                            "<a href='http://localhost:8080/api/v1/users/activate/%s?email=%s"
                                    .formatted(verificationCode, email) + "'>Verify Now</a>",
                    true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
