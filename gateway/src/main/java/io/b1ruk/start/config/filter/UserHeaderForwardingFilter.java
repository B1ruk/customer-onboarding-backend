package io.b1ruk.start.config.filter;

import io.b1ruk.start.config.JwtConfig;
import io.b1ruk.start.model.entity.UserEntity;
import io.b1ruk.start.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class UserHeaderForwardingFilter implements GlobalFilter, Ordered {

    public static final String X_USER_ROLES = "X-User-Roles";
    public static final String X_USER_ID = "X-User-Id";

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var headers = exchange.getRequest().getHeaders();
        var authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            var jwt = authorizationHeader.substring(7);
            String userId = jwtConfig.extractUsername(jwt);

            UserEntity userEntity = userRepository.findByUsername(userId);

            var modifiedRequest = exchange.getRequest()
                    .mutate()
                    .header(X_USER_ID, userEntity.getUsername())
                    .header(X_USER_ROLES, userEntity.getRoles())
                    .build();

            log.info("adding ,X_USER_ID,X_USER_ROLES");

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
