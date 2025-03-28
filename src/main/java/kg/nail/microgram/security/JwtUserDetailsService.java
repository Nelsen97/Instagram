package kg.nail.microgram.security;

import kg.nail.microgram.entity.User;
import kg.nail.microgram.exception.NotFoundException;
import kg.nail.microgram.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("Пользователя с имеилом %s не существует в базе данных".formatted(email)));

        return JwtEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().getAuthority())
                .fullName(user.getFullName())
                .enabled(user.getIsEnabled())
                .build();
    }
}
