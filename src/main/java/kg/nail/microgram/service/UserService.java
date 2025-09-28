package kg.nail.microgram.service;

import kg.nail.microgram.dto.auth.request.LoginRequestDTO;
import kg.nail.microgram.dto.auth.response.LoginResponse;
import kg.nail.microgram.dto.user.request.SearchUsersByFilter;
import kg.nail.microgram.dto.user.request.UserRegistrationDTO;
import kg.nail.microgram.dto.user.response.UserRegistrationResponse;
import kg.nail.microgram.dto.user.response.UsersByFilterResponse;
import kg.nail.microgram.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User getUserById(Long id);

    User getUserByEmail(String email);

    UserRegistrationResponse registration(UserRegistrationDTO userRegistrationDTO);

    LoginResponse login(LoginRequestDTO loginRequestDTO);

    String activateUser(String email, String verificationCode);

    Page<UsersByFilterResponse> searchUsersByFilter(SearchUsersByFilter usersFilter, Pageable pageable);
}
