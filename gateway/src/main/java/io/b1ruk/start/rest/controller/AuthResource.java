package io.b1ruk.start.rest.controller;

import io.b1ruk.start.rest.restData.AuthResponse;
import io.b1ruk.start.rest.restData.UserCredential;
import io.b1ruk.start.service.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("authResource")
@AllArgsConstructor
public class AuthResource {

    private AuthService authService;


    @PostMapping(value = "",produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AuthResponse>> authenticateUser(@RequestBody UserCredential userCredential) throws Exception {
        return Mono.just(authService.authenticate(userCredential.username(), userCredential.password()))
                .map(authResponse -> ResponseEntity.ok().body(authResponse));
    }
}
