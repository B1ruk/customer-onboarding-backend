package io.b1ruk.start.config.filter;

import io.b1ruk.start.config.JwtConfig;
import io.b1ruk.start.model.entity.UserEntity;
import io.b1ruk.start.model.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    public static final String X_USER_ROLES = "X-User-Roles";
    public static final String X_USER_ID = "X-User-Id";

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            String userId = jwtConfig.extractUsername(jwt);

            UserEntity userEntity = userRepository.findByUsername(userId);

            response.addHeader(X_USER_ROLES, userEntity.getRoles());
            response.addHeader(X_USER_ID, userId);
        }
        filterChain.doFilter(request, response);
    }
}
