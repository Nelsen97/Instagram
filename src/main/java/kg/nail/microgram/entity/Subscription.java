package kg.nail.microgram.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "subscriber_subscription")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Subscription extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    User subscriber;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    User subscription;
}
