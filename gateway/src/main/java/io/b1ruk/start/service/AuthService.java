package io.b1ruk.start.service;

import io.b1ruk.start.config.JwtConfig;
import io.b1ruk.start.model.entity.UserEntity;
import io.b1ruk.start.model.repository.UserRepository;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtConfig jwtConfig;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
    }

    public String authenticate(String username, String password) throws Exception {
        UserEntity user = userRepository.findByUsername(username);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ForbiddenException("Invalid password");
        }

        return jwtConfig.generateToken(user);
    }
}

