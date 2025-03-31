package kg.nail.microgram.mapper;

import kg.nail.microgram.dto.comment.response.CommentPublicationResponse;
import kg.nail.microgram.dto.publication.response.PublicationListResponse;
import kg.nail.microgram.dto.publication.response.PublicationResponse;
import kg.nail.microgram.dto.user.response.UserPublicationResponse;
import kg.nail.microgram.entity.Publication;
import kg.nail.microgram.service.CommentService;
import kg.nail.microgram.service.LikeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicationMapper {
    LikeService likeService;
    CommentService commentService;


    public PublicationResponse publicationEntityToPublicationResponse(Publication publication) {
        List<CommentPublicationResponse> comments = commentService.getCommentsByPublicationId(publication.getId());
        return PublicationResponse.builder()
                .id(publication.getId())
                .photoName(publication.getPhotoName())
                .createdAt(publication.getCreatedAt())
                .updatedAt(publication.getUpdatedAt())
                .description(publication.getDescription())
                .author(UserPublicationResponse.builder()
                        .id(publication.getUser().getId())
                        .email(publication.getUser().getEmail())
                        .fullName(publication.getUser().getFullName())
                        .build())
                .comments(comments)
                .countLikes(likeService.countLikesByPublicationId(publication.getId()))
                .countComments(comments.size())
                .build();
    }

    public List<PublicationListResponse> publicationEntityToPublicationListResponse(List<Publication> publications) {
        return publications.stream()
                .map(publication -> PublicationListResponse.builder()
                        .id(publication.getId())
                        .photoName(publication.getPhotoName())
                        .createdAt(publication.getCreatedAt())
                        .updatedAt(publication.getUpdatedAt())
                        .description(publication.getDescription())
                        .author(UserPublicationResponse.builder()
                                .id(publication.getUser().getId())
                                .email(publication.getUser().getEmail())
                                .fullName(publication.getUser().getFullName())
                                .build())
                        .comments(commentService.getCommentsByPublicationId(publication.getId()))
                        .countLikes(likeService.countLikesByPublicationId(publication.getId()))
                        .build())
                .toList();
    }
}
