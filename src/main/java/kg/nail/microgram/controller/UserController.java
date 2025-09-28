package kg.nail.microgram.controller;

import jakarta.validation.Valid;
import kg.nail.microgram.dto.auth.request.LoginRequestDTO;
import kg.nail.microgram.dto.auth.response.LoginResponse;
import kg.nail.microgram.dto.user.request.SearchUsersByFilter;
import kg.nail.microgram.dto.user.request.UserRegistrationDTO;
import kg.nail.microgram.dto.user.response.UserRegistrationResponse;
import kg.nail.microgram.dto.user.response.UsersByFilterResponse;
import kg.nail.microgram.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/activate/{code}")
    public ResponseEntity<String> activateUser(@RequestParam String email, @PathVariable String code) {
        return ResponseEntity.ok().body(userService.activateUser(email, code));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<UsersByFilterResponse>> searchUsers(@RequestBody SearchUsersByFilter usersFilter,
                                                                   @PageableDefault Pageable pageable) {
        return new ResponseEntity<>(userService.searchUsersByFilter(usersFilter, pageable), HttpStatus.OK);
    }
}
