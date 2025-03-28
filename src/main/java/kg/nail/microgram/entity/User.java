package kg.nail.microgram.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import kg.nail.microgram.enums.Role;
import kg.nail.microgram.enums.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Enumerated(EnumType.STRING)
    Role role;
    @Email
    @Column(name = "email", unique = true, nullable = false, length = 50)
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "full_name", nullable = false, length = 100)
    String fullName;

    @Column(nullable = false, length = 100)
    String address;

    @Pattern(regexp = "^\\+996\\d{9}$")
    @Column(name = "phone_number", nullable = false, length = 13)
    String phoneNumber;

    @Builder.Default
    Boolean isEnabled = true;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    UserStatus userStatus = UserStatus.ACTIVE;
}
