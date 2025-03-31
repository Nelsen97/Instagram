package kg.nail.microgram.service;

import kg.nail.microgram.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublicationService {
    Publication getPublicationById(Long id);

    Page<Publication> getAll(Pageable pageable);

    void save(Publication publication);

    Page<Publication> getPublicationsBySubscriptionIds(Pageable pageable, List<Long> subscriptionIds);
}
