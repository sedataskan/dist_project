package dist_system.movie_app.controller;

import dist_system.movie_app.dto.LoginRequest;
import dist_system.movie_app.dto.RegisterRequest;
import dist_system.movie_app.model.BaseResponse;
import dist_system.movie_app.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("app/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    // register
    @PostMapping("/register")
    public BaseResponse register(@RequestBody RegisterRequest registerRequest) {
        BaseResponse response = service.register(registerRequest);
        if (response == null) {
            return BaseResponse
                    .builder()
                    .status(false)
                    .message("User already exists")
                    .payload(null)
                    .build();
        }
        return BaseResponse
                .builder()
                .status(true)
                .message("User created")
                .payload(response)
                .build();
    }

    // login
    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginRequest loginRequest) {
        String response = service.login(loginRequest);
        if (response == null) {
            return BaseResponse
                    .builder()
                    .status(false)
                    .message("User not found")
                    .payload(null)
                    .build();
        }
        return BaseResponse
                .builder()
                .status(true)
                .message("User logged in")
                .payload(response)
                .build();
    }
}
