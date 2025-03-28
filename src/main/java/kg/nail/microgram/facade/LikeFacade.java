package kg.nail.microgram.facade;

public interface LikeFacade {

    void addLikeByPublicationIdAndUserId(Long publicationId, Long userId);
    void removeLikeByPublicationIdAndUserId(Long publicationId, Long userId);
}
