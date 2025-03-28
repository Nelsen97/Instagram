package kg.nail.microgram.service.impl;

import kg.nail.microgram.entity.Like;
import kg.nail.microgram.exception.NotFoundException;
import kg.nail.microgram.repository.LikeRepository;
import kg.nail.microgram.service.LikeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LikeServiceImpl implements LikeService {
    final LikeRepository likeRepository;
    @Override
    public long countLikesByPublicationId(Long publicationId) {
        return likeRepository.countLikeByPublicationId(publicationId);
    }

    @Override
    public void save(Like like) {
        likeRepository.save(like);
    }

    @Override
    public boolean existsLikeByPublicationIdAndUserId(Long publicationId, Long userId) {
        return likeRepository.existsLikeByPublicationIdAndUserId(publicationId, userId);
    }

    @Override
    public Like getLikeByPublicationIdAndUserId(Long publicationId, Long userId) {
        return likeRepository.findLikeByPublicationIdAndUserId(publicationId, userId).orElseThrow(
                () -> new NotFoundException("Лайка на публикацию с id: %d от пользователя: %d не сушествует".formatted(publicationId, userId))
        );
    }
}
