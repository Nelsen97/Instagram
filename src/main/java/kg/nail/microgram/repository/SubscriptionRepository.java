package kg.nail.microgram.repository;

import kg.nail.microgram.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsBySubscriberIdAndSubscriptionId(Long subscriber, Long subscriptionId);

    void deleteBySubscriberIdAndSubscriptionId(Long subscriber, Long subscriptionId);

    Page<Subscription> findBySubscriberId(Pageable pageable, Long subscriberId);

}
