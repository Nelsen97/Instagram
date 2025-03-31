package kg.nail.microgram.service;

import kg.nail.microgram.entity.Like;
import kg.nail.microgram.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeService {
    long countLikesByPublicationId(Long publicationId);
    //ToDo List LikeListByUsers
    void save(Like like);

    boolean existsLikeByPublicationIdAndUserId(Long publicationId, Long userId);

    Like getLikeByPublicationIdAndUserId(Long publicationId, Long userId);

    Page<Publication> getPublicationsLikedByUserId(Pageable pageable, Long userId);
}
