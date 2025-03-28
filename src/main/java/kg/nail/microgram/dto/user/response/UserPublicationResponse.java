package kg.nail.microgram.dto.user.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPublicationResponse {
    Long id;
    String fullName;
    String email;
}
