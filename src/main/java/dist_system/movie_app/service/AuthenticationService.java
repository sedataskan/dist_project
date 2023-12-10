package dist_system.movie_app.service;

import dist_system.movie_app.dto.AuthenticationResponse;
import dist_system.movie_app.dto.LoginRequest;
import dist_system.movie_app.dto.RegisterRequest;
import dist_system.movie_app.repository.UserRepository;
import dist_system.movie_app.user.Role;
import dist_system.movie_app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        //check if user exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            //throw exception in console
            System.out.println("User already exists");
            return null;
        }
        else {
            //create user
            var user = User.builder()
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(Role.USER)
                    .build();
            //save user
            userRepository.save(user);
            //generate token
            var jwtToken = jwtService.generateToken(user);
            //return token
            return AuthenticationResponse
                    .builder()
                    .accessToken(jwtToken)
                    .build();
        }
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );
        var user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }
}
