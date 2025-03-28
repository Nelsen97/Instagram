package kg.nail.microgram.dto.publication.response;

import kg.nail.microgram.dto.comment.response.CommentPublicationResponse;
import kg.nail.microgram.dto.user.response.UserPublicationResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicationResponse {
    Long id;
    String photoName;
    String description;
    UserPublicationResponse author;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long countLikes;
    List<CommentPublicationResponse> comments;
}
