package kg.nail.microgram.dto.comment.request;

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
public class CommentCreateRequestDTO {

    @NotBlank(message = "Описание не может быть пустым")
    @Size(min = 3, max = 100, message = "Длина комментария не должна быть меньше 3 и больше 100 символов")
    String comment;
}
