package kg.nail.microgram.dto.comment.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentCreateResponse {
    Long id;
    String comment;
    String fullName;
    LocalDateTime createdAt;
}
