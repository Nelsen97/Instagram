package kg.nail.microgram.service.impl;

import kg.nail.microgram.dto.comment.response.CommentPublicationResponse;
import kg.nail.microgram.entity.Comment;
import kg.nail.microgram.exception.NotFoundException;
import kg.nail.microgram.mapper.CommentMapper;
import kg.nail.microgram.repository.CommentRepository;
import kg.nail.microgram.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentServiceImpl implements CommentService {
    final CommentRepository commentRepository;
    final CommentMapper commentMapper;


    @Override
    public List<CommentPublicationResponse> getCommentsByPublicationId(Long publicationId) {
        List<Comment> comments = commentRepository.findByPublicationId(publicationId);

        return commentMapper.commentEntityToCommentPublicationResponse(comments);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment getById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Коммент с id: %d не существует в базе".formatted(id))
        );
    }

}
