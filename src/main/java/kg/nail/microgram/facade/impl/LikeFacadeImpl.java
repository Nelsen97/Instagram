package kg.nail.microgram.facade.impl;

import kg.nail.microgram.entity.Like;
import kg.nail.microgram.entity.Publication;
import kg.nail.microgram.entity.User;
import kg.nail.microgram.exception.BadRequestException;
import kg.nail.microgram.facade.LikeFacade;
import kg.nail.microgram.service.LikeService;
import kg.nail.microgram.service.PublicationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LikeFacadeImpl implements LikeFacade {
    final LikeService likeService;
    final PublicationService publicationService;

    public void addLikeByPublicationIdAndUserId(Long publicationId, Long userId) {
        Publication publication = publicationService.getPublicationById(publicationId);
        if (likeService.existsLikeByPublicationIdAndUserId(publicationId, userId)) {
            throw new BadRequestException("Вы уже поставили лайк к публикации с id: %d".formatted(publicationId));
        }
        Like like = new Like();
        like.setPublication(publication);
        like.setUser(User.builder().id(userId).build());

        likeService.save(like);
    }

    @Override
    public void removeLikeByPublicationIdAndUserId(Long publicationId, Long userId) {
        Like likeRemoved = likeService.getLikeByPublicationIdAndUserId(publicationId, userId);
        likeRemoved.setDeletedAt(LocalDateTime.now());
        likeService.save(likeRemoved);
    }
}
