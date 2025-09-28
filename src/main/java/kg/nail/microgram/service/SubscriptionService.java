package kg.nail.microgram.service;

import kg.nail.microgram.entity.Subscription;
import kg.nail.microgram.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscriptionService {
    void subscribe(Subscription subscription);

    void unsubscribe(Long subscriber, Long subscriptionId);

    boolean existsBySubscriberAndSubscriptionId(Long subscriber, Long subscriptionId);

    Page<User> getSubscriptionsBySubscriberId(Pageable pageable, Long subscriberId);

    long countBySubscriberId(Long subscriberId);
}
