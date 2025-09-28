package kg.nail.microgram.service;

public interface EmailService {
    void sendVerificationEmail(String toEmail, String verificationCode);
}
