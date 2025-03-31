package kg.nail.microgram.repository;

import kg.nail.microgram.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    long countLikeByPublicationId(Long publicationId);

    boolean existsLikeByPublicationIdAndUserId(Long publicationId, Long userId);

    Optional<Like> findLikeByPublicationIdAndUserId(Long publicationId, Long userId);

    Page<Like> findLikeByUserId(Pageable pageable, Long userId);
}
