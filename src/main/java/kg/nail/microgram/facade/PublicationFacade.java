package kg.nail.microgram.facade;

import kg.nail.microgram.dto.publication.request.PublicationCreateRequestDTO;
import kg.nail.microgram.dto.publication.request.PublicationUpdateRequestDTO;
import kg.nail.microgram.dto.publication.response.PublicationListResponse;
import kg.nail.microgram.dto.publication.response.PublicationResponse;
import kg.nail.microgram.security.JwtEntity;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PublicationFacade {
    PublicationResponse getPublication(Long publicationId);

    Page<PublicationListResponse> getAllPublications(Pageable pageable);

    void createPublication(MultipartFile[] files,
                           PublicationCreateRequestDTO publicationCreateRequestDTO,
                           JwtEntity currentUser);

    void updatePublication(Long id, PublicationUpdateRequestDTO publicationUpdateRequestDTO);

    void deletePublicationById(Long id);

    Resource getPublicationImageByPhotoName(String photoName);

    Page<PublicationListResponse> getLikedPublications(Pageable pageable, JwtEntity jwtEntity);

    Page<PublicationListResponse> getPublicationsBySubscriptions(Pageable pageable, JwtEntity jwtEntity);
}
