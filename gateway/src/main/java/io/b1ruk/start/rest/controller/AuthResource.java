package io.b1ruk.start.rest.controller;

import io.b1ruk.start.rest.restData.UserCredential;
import io.b1ruk.start.service.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("authResource")
@AllArgsConstructor
public class AuthResource {

    private AuthService authService;


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> authenticateUser(@RequestBody UserCredential userCredential) throws Exception {
        return Mono.just(authService.authenticate(userCredential.username(), userCredential.password()))
                .map(token -> ResponseEntity.ok().body(token))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
    }
}
