package kg.nail.microgram.controller;

import jakarta.validation.Valid;
import kg.nail.microgram.dto.auth.request.LoginRequestDTO;
import kg.nail.microgram.dto.auth.response.LoginResponse;
import kg.nail.microgram.dto.user.request.UserRegistrationDTO;
import kg.nail.microgram.dto.user.response.UserRegistrationResponse;
import kg.nail.microgram.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserController {
    final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserRegistrationResponse> register(@Valid @RequestBody
                                                                    UserRegistrationDTO userRegistrationDTO) {
        return new ResponseEntity<>(userService.registration(userRegistrationDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return new ResponseEntity<>(userService.login(loginRequestDTO), HttpStatus.OK);
    }
}
