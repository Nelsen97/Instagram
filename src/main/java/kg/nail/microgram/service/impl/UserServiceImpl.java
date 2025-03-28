package kg.nail.microgram.service.impl;

import kg.nail.microgram.dto.auth.request.LoginRequestDTO;
import kg.nail.microgram.dto.auth.response.LoginResponse;
import kg.nail.microgram.dto.user.request.UserRegistrationDTO;
import kg.nail.microgram.dto.user.response.UserRegistrationResponse;
import kg.nail.microgram.entity.User;
import kg.nail.microgram.exception.BadRequestException;
import kg.nail.microgram.exception.NotFoundException;
import kg.nail.microgram.mapper.UserMapper;
import kg.nail.microgram.repository.UserRepository;
import kg.nail.microgram.security.JwtEntity;
import kg.nail.microgram.security.JwtService;
import kg.nail.microgram.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;
    final UserMapper userMapper;
    final JwtService jwtService;
    final AuthenticationManager authenticationManager;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь с id: %d не найден".formatted(id))
        );
    }

    @Override
    public UserRegistrationResponse registration(UserRegistrationDTO userRegistrationDTO) {
        if (userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            throw new BadRequestException("Имеил %s уже зарегистрован".formatted(userRegistrationDTO.getEmail()));
        }
        User user = userMapper.userRegistrationDTOToUser(userRegistrationDTO);
        userRepository.save(user);

        return userMapper.userToUserRegistrationResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequestDTO loginRequestDTO) {
        JwtEntity jwtEntity = (JwtEntity) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginRequestDTO.getEmail(), loginRequestDTO.getPassword())).getPrincipal();

        String accessToken = jwtService.generateAccessToken(jwtEntity);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
