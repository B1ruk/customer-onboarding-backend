package io.b1ruk.start.config.filter;


import io.b1ruk.start.config.JwtAuthenticationToken;
import io.b1ruk.start.config.JwtConfig;
import io.b1ruk.start.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtConfig jwtConfig;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            String username = jwtConfig.extractUsername(authToken);

            if (username != null && jwtConfig.validateToken(authToken, username)) {
                var roles=jwtConfig.extractAllClaims(authToken).get("roles");

                UserEntity user = new UserEntity(username, "", (String) roles); // Adjust roles/authorities as needed

                JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(user, authToken, toGrantedAuthority(user));

                // Build the SecurityContext with the authenticated token
                SecurityContext securityContext = new SecurityContextImpl(authenticationToken);


                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
            }
        }
        return chain.filter(exchange);
    }

    private List<SimpleGrantedAuthority> toGrantedAuthority(UserEntity userEntity) {
        return Stream.of(userEntity.getRoles())
                .map("role_"::concat)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
