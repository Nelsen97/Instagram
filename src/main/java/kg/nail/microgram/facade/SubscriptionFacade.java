package kg.nail.microgram.facade;

public interface SubscriptionFacade {

    void subscribe(Long targetUserId, Long subscriptionId);
    void unsubscribe(Long targetUserId, Long subscriptionId);
}
