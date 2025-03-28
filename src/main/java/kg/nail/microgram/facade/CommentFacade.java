package kg.nail.microgram.facade;

import kg.nail.microgram.dto.comment.request.CommentCreateRequestDTO;
import kg.nail.microgram.dto.comment.request.CommentUpdateRequestDTO;
import kg.nail.microgram.dto.comment.response.CommentCreateResponse;
import kg.nail.microgram.dto.comment.response.CommentUpdateResponse;
import kg.nail.microgram.security.JwtEntity;

public interface CommentFacade {
    CommentCreateResponse createCommentForPublication(Long publicationId,
                                                      CommentCreateRequestDTO commentCreateRequestDTO,
                                                      JwtEntity jwtEntity);

    CommentUpdateResponse updateComment(Long id,
                                        CommentUpdateRequestDTO commentUpdateRequestDTO,
                                        JwtEntity jwtEntity);

    void deleteComment(Long id, JwtEntity jwtEntity);
}
