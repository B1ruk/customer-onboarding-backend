package io.b1ruk.start.rest.controller;

import io.b1ruk.start.rest.restData.UserCredential;
import io.b1ruk.start.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authResource")
@RequiredArgsConstructor
public class AuthResource {

    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserCredential userCredential) throws Exception {
            String token = authService.authenticate(userCredential.username(), userCredential.password());
            return ResponseEntity.ok().body(token);
    }
}
