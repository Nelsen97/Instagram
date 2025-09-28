package kg.nail.microgram.service.impl;

import kg.nail.microgram.dto.auth.request.LoginRequestDTO;
import kg.nail.microgram.dto.auth.response.LoginResponse;
import kg.nail.microgram.dto.user.request.SearchUsersByFilter;
import kg.nail.microgram.dto.user.request.UserRegistrationDTO;
import kg.nail.microgram.dto.user.response.UserRegistrationResponse;
import kg.nail.microgram.dto.user.response.UsersByFilterResponse;
import kg.nail.microgram.entity.User;
import kg.nail.microgram.enums.UserStatus;
import kg.nail.microgram.exception.BadRequestException;
import kg.nail.microgram.exception.NotFoundException;
import kg.nail.microgram.mapper.UserMapper;
import kg.nail.microgram.repository.UserRepository;
import kg.nail.microgram.security.JwtEntity;
import kg.nail.microgram.security.JwtService;
import kg.nail.microgram.service.EmailService;
import kg.nail.microgram.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;
    final UserMapper userMapper;
    final JwtService jwtService;
    final AuthenticationManager authenticationManager;
    final EmailService emailService;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь с id: %d не найден".formatted(id))
        );
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Пользователь с email: %s не найден".formatted(email))
        );
    }

    @Transactional
    @Override
    public UserRegistrationResponse registration(UserRegistrationDTO userRegistrationDTO) {
        if (userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            throw new BadRequestException("Имеил %s уже зарегистрован".formatted(userRegistrationDTO.getEmail()));
        }
        if (userRepository.existsByUsername(userRegistrationDTO.getUsername())) {
            throw new BadRequestException("Username %s уже зарегистрован".formatted(userRegistrationDTO.getUsername()));
        }

        User user = userMapper.userRegistrationDTOToUser(userRegistrationDTO);
        userRepository.save(user);
        emailService.sendVerificationEmail(userRegistrationDTO.getEmail(), user.getVerificationCode());
        return userMapper.userToUserRegistrationResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequestDTO loginRequestDTO) {
        JwtEntity jwtEntity = (JwtEntity) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginRequestDTO.getEmail(), loginRequestDTO.getPassword())).getPrincipal();

        String accessToken = jwtService.generateAccessToken(jwtEntity);
        String refreshToken = jwtService.generateRefreshToken(jwtEntity);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public String activateUser(String email, String verificationCode) {
        User user = getUserByEmail(email);
        if (user.getVerificationCode().equals(verificationCode)) {
            user.setIsEnabled(true);
            user.setUserStatus(UserStatus.ACTIVE);
            userRepository.save(user);
            return "Ваша учетная запись успешно активирована!";
        }
        return "Неверный код активации или имеил";
    }

    @Override
    public Page<UsersByFilterResponse> searchUsersByFilter(SearchUsersByFilter usersFilter, Pageable pageable) {
        return userRepository.findAll(usersFilter, pageable)
                .map(userMapper::userToUsersByFilterResponse);
    }
}
