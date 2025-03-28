package kg.nail.microgram.controller;

import kg.nail.microgram.facade.impl.LikeFacadeImpl;
import kg.nail.microgram.security.JwtEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/likes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LikeController {

    LikeFacadeImpl likeFacade;

    @PostMapping("/{publicationId}/add")
    public ResponseEntity<Void> addLikeForPublication(@PathVariable("publicationId") Long publicationId,
                                                      @AuthenticationPrincipal JwtEntity jwtEntity) {
        likeFacade.addLikeByPublicationIdAndUserId(publicationId, jwtEntity.getId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{publicationId}/delete")
    public ResponseEntity<Void> deleteLikeForPublication(@PathVariable("publicationId") Long publicationId,
                                              @AuthenticationPrincipal JwtEntity jwtEntity) {
        likeFacade.removeLikeByPublicationIdAndUserId(publicationId, jwtEntity.getId());

        return ResponseEntity.noContent().build();
    }
}
