package kg.nail.microgram.service;

import kg.nail.microgram.entity.Like;

public interface LikeService {
    long countLikesByPublicationId(Long publicationId);
    //ToDo List LikeListByUsers
    void save(Like like);

    boolean existsLikeByPublicationIdAndUserId(Long publicationId, Long userId);

    Like getLikeByPublicationIdAndUserId(Long publicationId, Long userId);
}
