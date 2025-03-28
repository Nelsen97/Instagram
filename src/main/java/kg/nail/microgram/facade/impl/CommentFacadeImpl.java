package kg.nail.microgram.facade.impl;

import kg.nail.microgram.dto.comment.request.CommentCreateRequestDTO;
import kg.nail.microgram.dto.comment.request.CommentUpdateRequestDTO;
import kg.nail.microgram.dto.comment.response.CommentCreateResponse;
import kg.nail.microgram.dto.comment.response.CommentUpdateResponse;
import kg.nail.microgram.entity.Comment;
import kg.nail.microgram.entity.Publication;
import kg.nail.microgram.entity.User;
import kg.nail.microgram.exception.BadRequestException;
import kg.nail.microgram.facade.CommentFacade;
import kg.nail.microgram.mapper.CommentMapper;
import kg.nail.microgram.security.JwtEntity;
import kg.nail.microgram.service.CommentService;
import kg.nail.microgram.service.PublicationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentFacadeImpl implements CommentFacade {
    final PublicationService publicationService;
    final CommentService commentService;
    final CommentMapper commentMapper;

    @Override
    public CommentCreateResponse createCommentForPublication(Long publicationId,
                                                             CommentCreateRequestDTO commentCreateRequestDTO,
                                                             JwtEntity jwtEntity) {
        Publication publication = publicationService.getPublicationById(publicationId);
        Comment comment = commentMapper.commentCreateRequestToCommentEntity(commentCreateRequestDTO);
        comment.setPublication(publication);
        comment.setUser(User.builder()
                .id(jwtEntity.getId())
                .fullName(jwtEntity.getFullName())
                .build());

        Comment commentSaved = commentService.save(comment);

        return commentMapper.commentEntityToCommentCreateResponse(commentSaved);
    }

    @Override
    public CommentUpdateResponse updateComment(Long id, CommentUpdateRequestDTO commentUpdateRequestDTO, JwtEntity jwtEntity) {
        Comment comment = commentService.getById(id);
        if (!comment.getUser().getId().equals(jwtEntity.getId())) {
            throw new BadRequestException("Только автор комментария может его редактировать");
        }
        comment.setComment(commentUpdateRequestDTO.getComment());
        Comment commentUpdated = commentService.save(comment);

        return commentMapper.commentEntityToCommentUpdateResponse(commentUpdated);
    }

    @Override
    public void deleteComment(Long id, JwtEntity jwtEntity) {
        Comment comment = commentService.getById(id);
        if (!comment.getUser().getId().equals(jwtEntity.getId())) {
            throw new BadRequestException("Только автор комментария может его удалить");
        }
        comment.setDeletedAt(LocalDateTime.now());
        commentService.save(comment);
    }
}
