package kg.nail.microgram.service;

import kg.nail.microgram.entity.Subscription;

public interface SubscriptionService {
    void subscribe(Subscription subscription);
    void unsubscribe(Long subscriber, Long subscriptionId);
    boolean existsBySubscriberAndSubscriptionId(Long subscriber, Long subscriptionId);
}
