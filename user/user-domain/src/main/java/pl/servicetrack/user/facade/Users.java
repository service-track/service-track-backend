package pl.servicetrack.user.facade;

import io.vavr.control.Either;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.servicetrack.BaseError;
import pl.servicetrack.user.db.UserRepository;
import pl.servicetrack.user.error.UserDomainError;
import pl.servicetrack.user.model.User;
import pl.servicetrack.security.JwtService;

import java.util.Objects;

public class Users {

    private final UserRepository userRepository;
    private final UserMapper USER_MAPPER = UserMapper.INSTANCE;
    private final PasswordEncoder passwordEncoder;

    public Users(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Either<BaseError, User> register(User user) {
        if (userRepository.exists(user.id())) {
            return Either.left(new UserDomainError.UserAlreadyExists());
        }

        return userRepository.save(USER_MAPPER.userToUserEntity(user), passwordEncoder)
                .map(response -> user);
    }

    public Either<BaseError, String> login(
            String email,
            String password,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        return userRepository.findByEmail(email)
                .filterOrElse(Objects::nonNull, error -> new UserDomainError.UserNotFound())
                .map(user -> {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.id(), password));
                    return jwtService.generateToken(USER_MAPPER.userEntityToUser(user));
                });
    }
}
