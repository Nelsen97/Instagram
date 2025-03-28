package kg.nail.microgram.service;

import kg.nail.microgram.dto.auth.request.LoginRequestDTO;
import kg.nail.microgram.dto.auth.response.LoginResponse;
import kg.nail.microgram.dto.user.request.UserRegistrationDTO;
import kg.nail.microgram.dto.user.response.UserRegistrationResponse;
import kg.nail.microgram.entity.User;

public interface UserService {
    User getUserById(Long id);

    UserRegistrationResponse registration(UserRegistrationDTO userRegistrationDTO);

    LoginResponse login(LoginRequestDTO loginRequestDTO);
}
