package kg.nail.microgram.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.security.SecureRandom;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationCodeGenerator {
    static String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    static int CODE_LENGTH = 6;
    static SecureRandom random = new SecureRandom();

    public static String generateVerificationCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }


}
