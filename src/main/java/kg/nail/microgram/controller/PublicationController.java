package kg.nail.microgram.controller;

import jakarta.validation.Valid;
import kg.nail.microgram.dto.publication.request.PublicationCreateRequestDTO;
import kg.nail.microgram.dto.publication.request.PublicationUpdateRequestDTO;
import kg.nail.microgram.dto.publication.response.PublicationListResponse;
import kg.nail.microgram.dto.publication.response.PublicationResponse;
import kg.nail.microgram.facade.PublicationFacade;
import kg.nail.microgram.security.JwtEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/publications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PublicationController {
    PublicationFacade publicationFacade;

    @GetMapping("/{id}")
    public ResponseEntity<PublicationResponse> getPublication(@PathVariable("id") Long id) {
        return new ResponseEntity<>(publicationFacade.getPublication(id), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<PublicationListResponse>> getAllPublications(
            @PageableDefault Pageable pageable) {

        return new ResponseEntity<>(publicationFacade.getAllPublications(pageable), HttpStatus.OK);
    }

    @GetMapping("liked")
    public ResponseEntity<Page<PublicationListResponse>> getLikedPublications(@PageableDefault Pageable pageable,
                                                                              @AuthenticationPrincipal JwtEntity jwtEntity) {
        return new ResponseEntity<>(publicationFacade.getLikedPublications(pageable, jwtEntity), HttpStatus.OK);
    }

    @GetMapping("subscriptions")
    public ResponseEntity<Page<PublicationListResponse>> getPublicationsBySubscriptions(@PageableDefault Pageable pageable,
                                                                          @AuthenticationPrincipal JwtEntity jwtEntity) {

        return new ResponseEntity<>(publicationFacade.getPublicationsBySubscriptions(pageable, jwtEntity), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createPublication(@RequestPart("files") MultipartFile[] files,
                                                  @Valid @RequestBody PublicationCreateRequestDTO publicationCreateRequestDTO,
                                                  @AuthenticationPrincipal JwtEntity currentUser) {
        publicationFacade.createPublication(files, publicationCreateRequestDTO, currentUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updatePublication(@PathVariable("id") Long id,
                                                  @Valid @RequestBody PublicationUpdateRequestDTO publicationUpdateRequestDTO) {
        publicationFacade.updatePublication(id, publicationUpdateRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable("id") Long id) {
        publicationFacade.deletePublicationById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/photo/{photoName}")
    public ResponseEntity<Resource> getPhoto(@PathVariable("photoName") String photoName) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(publicationFacade.getPublicationImageByPhotoName(photoName));
    }
}
