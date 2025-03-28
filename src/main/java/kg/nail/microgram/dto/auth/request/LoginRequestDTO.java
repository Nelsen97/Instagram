package kg.nail.microgram.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequestDTO {

    @Size(min = 3, max = 50, message = "Имеил не может быть меньше 3 и больше 50 символов")
    @NotBlank(message = "Имеил не может быть пустым")
    @Email(message = "Имеил должен соответствовать формату mail@mail.ru")
    String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 3, max = 50, message = "Пароль не может быть меньше 3 и больше 50 символов")
    String password;
}
