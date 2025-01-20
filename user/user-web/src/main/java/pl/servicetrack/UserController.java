package pl.servicetrack;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.servicetrack.model.LoginRequest;
import pl.servicetrack.model.RegisterRequest;
import pl.servicetrack.model.UserControllerMapper;
import pl.servicetrack.security.JwtService;
import pl.servicetrack.user.facade.Users;
import pl.servicetrack.user.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class UserController {

    private final JwtService jwtService;
    private final UserControllerMapper userControllerMapper = UserControllerMapper.INSTANCE;
    private final Users users;
    private final AuthenticationManager authenticationManager;

    public UserController(JwtService jwtService, Users users, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.users = users;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return users.register(userControllerMapper.registerRequestBodyToUser(
                        registerRequest,
                        UUID.randomUUID(),
                        User.Role.USER,
                        LocalDateTime.now()))
                .fold(
                        UserResponseSolver::resolveError,
                        response -> ResponseEntity.status(CREATED).body(
                                userControllerMapper.userToRegisterResponse(response)
                        )
                );

    }

    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequest authenticationRequest) {
        return users.login(
                        authenticationRequest.email(),
                        authenticationRequest.password(),
                        authenticationManager,
                        jwtService)
                .fold(
                        UserResponseSolver::resolveError,
                        response -> ResponseEntity.status(CREATED).body(
                                userControllerMapper.tokenToAuthenticationResponse(response)
                        )
                );
    }
}
