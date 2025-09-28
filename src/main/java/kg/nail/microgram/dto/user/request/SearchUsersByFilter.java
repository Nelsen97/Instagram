package kg.nail.microgram.dto.user.request;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kg.nail.microgram.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUsersByFilter implements Specification<User> {

    String email;
    String username;
    String fullName;
    String address;

    @Override
    public Predicate toPredicate(@NonNull Root<User> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        Optional.ofNullable(this.email)
                .ifPresent(email ->
                        predicates.add(criteriaBuilder.like(root.get(User.Fields.email), "%" + email + "%")));

        Optional.ofNullable(this.username)
                .ifPresent(username ->
                        predicates.add(criteriaBuilder.equal(root.get(User.Fields.username), username)));

        Optional.ofNullable(this.fullName)
                .ifPresent(fullName ->
                        predicates.add(criteriaBuilder.like(root.get(User.Fields.fullName), "%" + fullName + "%")));

        Optional.ofNullable(this.address)
                .ifPresent(address ->
                        predicates.add(criteriaBuilder.like(root.get(User.Fields.address), "%" + address + "%")));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
