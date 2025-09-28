package kg.nail.microgram.mapper;

import kg.nail.microgram.dto.user.request.UserRegistrationDTO;
import kg.nail.microgram.dto.user.response.UserRegistrationResponse;
import kg.nail.microgram.dto.user.response.UsersByFilterResponse;
import kg.nail.microgram.entity.User;
import kg.nail.microgram.service.SubscriptionService;
import kg.nail.microgram.util.VerificationCodeGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserMapper {
    PasswordEncoder passwordEncoder;
    SubscriptionService subscriptionService;

    public User userRegistrationDTOToUser(UserRegistrationDTO userRegistrationDTO) {
        return User.builder()
                .email(userRegistrationDTO.getEmail())
                .password(passwordEncoder.encode(userRegistrationDTO.getPassword()))
                .username(userRegistrationDTO.getUsername())
                .role(userRegistrationDTO.getRole())
                .fullName(userRegistrationDTO.getFullName())
                .address(userRegistrationDTO.getAddress())
                .phoneNumber(userRegistrationDTO.getPhoneNumber())
                .verificationCode(VerificationCodeGenerator.generateVerificationCode())
                .build();
    }

    public UserRegistrationResponse userToUserRegistrationResponse(User user) {
        return UserRegistrationResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .userStatus(user.getUserStatus())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .fullName(user.getFullName())
                .build();
    }

    public UsersByFilterResponse userToUsersByFilterResponse(User user) {
        return UsersByFilterResponse.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .subscribersCount(subscriptionService.countBySubscriberId(user.getId()))
                .build();
    }
}
