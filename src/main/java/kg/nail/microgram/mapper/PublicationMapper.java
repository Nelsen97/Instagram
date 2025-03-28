package kg.nail.microgram.mapper;

import kg.nail.microgram.dto.publication.response.PublicationListResponse;
import kg.nail.microgram.dto.publication.response.PublicationResponse;
import kg.nail.microgram.dto.user.response.UserPublicationResponse;
import kg.nail.microgram.entity.Publication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublicationMapper {
    public PublicationResponse publicationEntityToPublicationResponse(Publication publication) {
        return PublicationResponse.builder()
                .id(publication.getId())
                .photoName(publication.getPhotoName())
                .createdAt(publication.getCreatedAt())
                .updatedAt(publication.getUpdatedAt())
                .description(publication.getDescription())
                .author(UserPublicationResponse.builder()
                        .id(publication.getUser().getId())
                        .email(publication.getUser().getEmail())
                        .fullName(publication.getUser().getFullName())
                        .build())
                .build();
    }

    public List<PublicationListResponse> publicationEntityToPublicationListResponse(List<Publication> publications) {
        return publications.stream()
                .map(publication -> PublicationListResponse.builder()
                        .id(publication.getId())
                        .photoName(publication.getPhotoName())
                        .createdAt(publication.getCreatedAt())
                        .updatedAt(publication.getUpdatedAt())
                        .description(publication.getDescription())
                        .author(UserPublicationResponse.builder()
                                .id(publication.getUser().getId())
                                .email(publication.getUser().getEmail())
                                .fullName(publication.getUser().getFullName())
                                .build())
                        .build())
                .toList();
    }
}
