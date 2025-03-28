package kg.nail.microgram.dto.user.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import kg.nail.microgram.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegistrationDTO {

    @Size(min = 3, max = 50, message = "Имеил не может быть меньше 3 и больше 50 символов")
    @NotBlank(message = "Имеил не может быть пустым")
    @Email(message = "Имеил должен соответствовать формату mail@mail.ru")
    String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 3, max = 50, message = "Пароль не может быть меньше 3 и больше 50 символов")
    String password;

    @NotNull(message = "Роль не может быть пустой")
    @Column(nullable = false)
    Role role;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 3, max = 100, message = "Имя не может быть меньше 3 и больше 100 символов")
    String fullName;

    @NotBlank(message = "Адресс не может быть пустым")
    @Size(min = 3, max = 100, message = "Адресс не может быть меньше 3 и больше 100 символов")
    String address;

    @NotBlank(message = "Номер телефона не может быть пустым")
    @Pattern(regexp = "^\\+996\\d{9}$",
            message = "Номер телефона должен соответствовать формату \"+996777000111\\")
    String phoneNumber;
}
