package kg.nail.microgram.facade.impl;

import kg.nail.microgram.entity.Subscription;
import kg.nail.microgram.entity.User;
import kg.nail.microgram.exception.BadRequestException;
import kg.nail.microgram.facade.SubscriptionFacade;
import kg.nail.microgram.service.SubscriptionService;
import kg.nail.microgram.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionFacadeImpl implements SubscriptionFacade {
    final SubscriptionService subscriptionService;
    final UserService userService;

    @Override
    public void subscribe(Long targetUserId, Long subscriptionId) {
        User user = userService.getUserById(targetUserId);
        
        if (targetUserId.equals(subscriptionId)) {
            throw new BadRequestException("Нельзя подписаться на самого себя");
        }

        if (subscriptionService.existsBySubscriberAndSubscriptionId(subscriptionId, targetUserId)) {
            throw new BadRequestException("Вы уже подписались на пользователя: %d".formatted(targetUserId));
        }

        Subscription subscription = new Subscription();
        subscription.setSubscriber(User.builder().id(subscriptionId).build());
        subscription.setSubscription(user);
        subscriptionService.subscribe(subscription);
    }

    @Transactional
    @Override
    public void unsubscribe(Long targetUserId, Long subscriptionId) {
        User user = userService.getUserById(targetUserId);
        subscriptionService.unsubscribe(subscriptionId, user.getId());
    }
}
