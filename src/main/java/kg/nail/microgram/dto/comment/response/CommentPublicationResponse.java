package kg.nail.microgram.dto.comment.response;

import kg.nail.microgram.dto.user.response.UserPublicationResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentPublicationResponse {
    String comment;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    UserPublicationResponse author;
}
