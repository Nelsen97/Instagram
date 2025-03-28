package kg.nail.microgram.enums;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"), USER("USER");

    String value;

    @Override
    public String getAuthority() {
        return getAuthorityWithPrefix();
    }

    private String getAuthorityWithPrefix() {
        return "ROLE_%s".formatted(this);
    }
}
