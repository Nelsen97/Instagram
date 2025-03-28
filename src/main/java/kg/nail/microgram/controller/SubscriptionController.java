package kg.nail.microgram.controller;

import kg.nail.microgram.facade.SubscriptionFacade;
import kg.nail.microgram.security.JwtEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/subscriptions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionController {

    SubscriptionFacade subscriptionFacade;

    @PostMapping("/{targetUserId}/subscribe")
    public ResponseEntity<Void> subscribe(@PathVariable("targetUserId") Long targetUserId,
                                    @AuthenticationPrincipal JwtEntity jwtEntity) {
        subscriptionFacade.subscribe(targetUserId, jwtEntity.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{targetUserId}/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@PathVariable("targetUserId") Long targetUserId,
                                          @AuthenticationPrincipal JwtEntity jwtEntity) {
        subscriptionFacade.unsubscribe(targetUserId, jwtEntity.getId());
        return ResponseEntity.noContent().build();
    }
}
