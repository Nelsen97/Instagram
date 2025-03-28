package kg.nail.microgram.dto.publication.request;

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
public class PublicationCreateRequestDTO {
    @NotBlank(message = "Описание не может быть пустым")
    @Size(min = 3, max = 100, message = "Описание не должно быть меньше 3 и больше 100 символов")
    String description;
}
