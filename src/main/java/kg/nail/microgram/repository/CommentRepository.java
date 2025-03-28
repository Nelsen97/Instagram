package kg.nail.microgram.repository;

import kg.nail.microgram.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPublicationId(Long publicationId);
}
