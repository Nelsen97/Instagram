package kg.nail.microgram.service.impl;

import kg.nail.microgram.entity.Subscription;
import kg.nail.microgram.entity.User;
import kg.nail.microgram.repository.SubscriptionRepository;
import kg.nail.microgram.service.SubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionServiceImpl implements SubscriptionService {
    final SubscriptionRepository subscriptionRepository;


    @Override
    public void subscribe(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    @Override
    public void unsubscribe(Long subscriber, Long subscriptionId) {
        subscriptionRepository.deleteBySubscriberIdAndSubscriptionId(subscriber, subscriptionId);
    }

    @Override
    public boolean existsBySubscriberAndSubscriptionId(Long subscriber, Long subscriptionId) {
        return subscriptionRepository.existsBySubscriberIdAndSubscriptionId(subscriber, subscriptionId);
    }

    @Override
    public Page<User> getSubscriptionsBySubscriberId(Pageable pageable, Long subscriberId) {

        return subscriptionRepository.findBySubscriberId(pageable, subscriberId).map(Subscription::getSubscriber);
    }
}
