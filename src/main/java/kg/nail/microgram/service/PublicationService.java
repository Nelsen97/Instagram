package kg.nail.microgram.service;

import kg.nail.microgram.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublicationService {
    Publication getPublicationById(Long id);

    Page<Publication> getAll(Pageable pageable);

    void save(Publication publication);
}
