package kg.nail.microgram.dto.user.response;

import kg.nail.microgram.enums.Role;
import kg.nail.microgram.enums.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegistrationResponse {

    Long id;
    String email;
    Role role;
    String fullName;
    String address;
    String phoneNumber;
    UserStatus userStatus;
}
