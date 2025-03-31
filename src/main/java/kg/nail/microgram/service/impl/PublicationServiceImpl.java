package kg.nail.microgram.service.impl;

import kg.nail.microgram.entity.Publication;
import kg.nail.microgram.exception.NotFoundException;
import kg.nail.microgram.repository.PublicationRepository;
import kg.nail.microgram.service.PublicationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicationServiceImpl implements PublicationService {
    final PublicationRepository publicationRepository;



    @Override
    public Publication getPublicationById(Long publicationId) {
        return publicationRepository.findById(publicationId).orElseThrow(
                () -> new NotFoundException("Публикация с id %d не существует в базе данных".formatted(publicationId)));
    }

    @Override
    public Page<Publication> getAll(Pageable pageable) {
        return publicationRepository.findAll(pageable);
    }

    @Override
    public void save(Publication publication) {
        publicationRepository.save(publication);
    }

    @Override
    public Page<Publication> getPublicationsBySubscriptionIds(Pageable pageable, List<Long> subscriptionIds) {
        return publicationRepository.findPublicationsByUserIdIn(pageable, subscriptionIds);
    }


}
