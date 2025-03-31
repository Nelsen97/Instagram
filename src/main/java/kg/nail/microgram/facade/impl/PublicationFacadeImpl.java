package kg.nail.microgram.facade.impl;

import kg.nail.microgram.dto.publication.request.PublicationCreateRequestDTO;
import kg.nail.microgram.dto.publication.request.PublicationUpdateRequestDTO;
import kg.nail.microgram.dto.publication.response.PublicationListResponse;
import kg.nail.microgram.dto.publication.response.PublicationResponse;
import kg.nail.microgram.entity.BaseEntity;
import kg.nail.microgram.entity.Publication;
import kg.nail.microgram.entity.User;
import kg.nail.microgram.exception.BadRequestException;
import kg.nail.microgram.exception.FileStorageException;
import kg.nail.microgram.exception.NotFoundException;
import kg.nail.microgram.facade.PublicationFacade;
import kg.nail.microgram.mapper.PublicationMapper;
import kg.nail.microgram.repository.PublicationRepository;
import kg.nail.microgram.security.JwtEntity;
import kg.nail.microgram.service.LikeService;
import kg.nail.microgram.service.PublicationService;
import kg.nail.microgram.service.SubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicationFacadeImpl implements PublicationFacade {
    final PublicationService publicationService;
    final PublicationMapper publicationMapper;
    final LikeService likeService;
    final SubscriptionService subscriptionService;

    @Value("${upload.dir.path}")
    String uploadDir;

    @Override
    public PublicationResponse getPublication(Long publicationId) {
        Publication publication = publicationService.getPublicationById(publicationId);

        return publicationMapper.publicationEntityToPublicationResponse(publication);
    }

    @Override
    public Page<PublicationListResponse> getAllPublications(Pageable pageable) {
        Page<Publication> publications = publicationService.getAll(pageable);
        List<PublicationListResponse> publicationListResponses =
                publicationMapper.publicationEntityToPublicationListResponse(publications.getContent());

        return new PageImpl<>(publicationListResponses, pageable, publications.getTotalElements());
    }

    @Override
    public void createPublication(MultipartFile[] files,
                                  PublicationCreateRequestDTO publicationCreateRequestDTO,
                                  JwtEntity currentUser) {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (final MultipartFile file : files) {
            if (file.getSize() == 0 || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
                throw new BadRequestException("Файл с наименованием %s не может быть пустым"
                        .formatted(file.getOriginalFilename()));
            }
            UUID uniqueFileName = UUID.randomUUID();
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filePath = String.format("%s%s%s", uploadDir, uniqueFileName, fileExtension);

            File destinationFile = new File(filePath);
            try {
                file.transferTo(destinationFile);
                publicationService.save(Publication.builder()
                        .photoName(String.format("%s%s", uniqueFileName, fileExtension))
                        .description(publicationCreateRequestDTO.getDescription())
                        .user(User.builder()
                                .id(currentUser.getId())
                                .build())
                        .build());
            } catch (IOException e) {
                throw new FileStorageException("Ошибка сохранения файла: " + file.getOriginalFilename());
            }
        }
    }

    @Override
    public void updatePublication(Long id, PublicationUpdateRequestDTO publicationUpdateRequestDTO) {
        Publication publication = publicationService.getPublicationById(id);
        publication.setDescription(publicationUpdateRequestDTO.getDescription());
        publicationService.save(publication);
    }

    @Override
    public void deletePublicationById(Long id) {
        Publication publication = publicationService.getPublicationById(id);
        publication.setDeletedAt(LocalDateTime.now());
        publicationService.save(publication);
    }

    @Override
    public Resource getPublicationImageByPhotoName(String photoName) {

        Path path = Paths.get(uploadDir, photoName);
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new NotFoundException("Фотография с наименованием: %s не найдена".formatted(photoName));
        }

    }

    @Override
    public Page<PublicationListResponse> getLikedPublications(Pageable pageable, JwtEntity jwtEntity) {
        Page<Publication> publicationsLikedByUserId = likeService.getPublicationsLikedByUserId(pageable, jwtEntity.getId());
        List<PublicationListResponse> publicationListResponses = publicationMapper.publicationEntityToPublicationListResponse(publicationsLikedByUserId.getContent());

        return new PageImpl<>(publicationListResponses, pageable, publicationsLikedByUserId.getTotalElements());
    }

    @Override
    public Page<PublicationListResponse> getPublicationsBySubscriptions(Pageable pageable, JwtEntity jwtEntity) {
        Page<User> userSubscriptions = subscriptionService.getSubscriptionsBySubscriberId(pageable, jwtEntity.getId());
        Page<Publication> publicationsBySubscriptionIds = publicationService.getPublicationsBySubscriptionIds
                (pageable, userSubscriptions.map(BaseEntity::getId).getContent());

        List<PublicationListResponse> publicationListResponses =
                publicationMapper.publicationEntityToPublicationListResponse(publicationsBySubscriptionIds.getContent());

        return new PageImpl<>(publicationListResponses, pageable, userSubscriptions.getTotalElements());
    }


}
