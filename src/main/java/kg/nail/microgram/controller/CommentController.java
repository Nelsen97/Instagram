package kg.nail.microgram.controller;

import jakarta.validation.Valid;
import kg.nail.microgram.dto.comment.request.CommentCreateRequestDTO;
import kg.nail.microgram.dto.comment.request.CommentUpdateRequestDTO;
import kg.nail.microgram.dto.comment.response.CommentCreateResponse;
import kg.nail.microgram.dto.comment.response.CommentUpdateResponse;
import kg.nail.microgram.facade.CommentFacade;
import kg.nail.microgram.security.JwtEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    CommentFacade commentFacade;

    @PostMapping("/{publicationId}/create")
    public ResponseEntity<CommentCreateResponse> createComment(@PathVariable("publicationId") Long publicationId,
                                                               @Valid @RequestBody CommentCreateRequestDTO commentCreateRequestDTO,
                                                               @AuthenticationPrincipal JwtEntity jwtEntity) {
        return new ResponseEntity<>(commentFacade.createCommentForPublication(publicationId,
                commentCreateRequestDTO, jwtEntity),
                HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentUpdateResponse> updateComment(@PathVariable("id") Long id,
                                                               @Valid @RequestBody CommentUpdateRequestDTO commentUpdateRequestDTO,
                                                               @AuthenticationPrincipal JwtEntity jwtEntity) {
        return new ResponseEntity<>(commentFacade.updateComment(id, commentUpdateRequestDTO, jwtEntity), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id,
                                                               @AuthenticationPrincipal JwtEntity jwtEntity) {
        commentFacade.deleteComment(id, jwtEntity);
        return ResponseEntity.noContent().build();

    }
}
