package kg.nail.microgram.repository;

import kg.nail.microgram.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
