package kg.nail.microgram.mapper;

import kg.nail.microgram.dto.comment.request.CommentCreateRequestDTO;
import kg.nail.microgram.dto.comment.response.CommentCreateResponse;
import kg.nail.microgram.dto.comment.response.CommentPublicationResponse;
import kg.nail.microgram.dto.comment.response.CommentUpdateResponse;
import kg.nail.microgram.dto.user.response.UserPublicationResponse;
import kg.nail.microgram.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentMapper {

    public List<CommentPublicationResponse> commentEntityToCommentPublicationResponse(List<Comment> comments) {
        return comments.stream()
                .map(comment -> CommentPublicationResponse.builder()
                        .createdAt(comment.getCreatedAt())
                        .updatedAt(comment.getUpdatedAt())
                        .comment(comment.getComment())

                        .author(UserPublicationResponse.builder()
                                .id(comment.getUser().getId())
                                .fullName(comment.getUser().getFullName())
                                .email(comment.getUser().getEmail())
                                .build())
                        .build())
                .toList();
    }

    public Comment commentCreateRequestToCommentEntity(CommentCreateRequestDTO commentCreateRequestDTO) {
        return Comment.builder()
                .comment(commentCreateRequestDTO.getComment())
                .build();
    }

    public CommentCreateResponse commentEntityToCommentCreateResponse(Comment comment) {
        return CommentCreateResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .fullName(comment.getUser().getFullName())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public CommentUpdateResponse commentEntityToCommentUpdateResponse(Comment comment) {
        return CommentUpdateResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .fullName(comment.getUser().getFullName())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
