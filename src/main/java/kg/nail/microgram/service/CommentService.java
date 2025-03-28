package kg.nail.microgram.service;

import kg.nail.microgram.dto.comment.response.CommentPublicationResponse;
import kg.nail.microgram.entity.Comment;

import java.util.List;

public interface CommentService {
        List<CommentPublicationResponse> getCommentsByPublicationId(Long publicationId);

    Comment save(Comment comment);

    Comment getById(Long id);
}
