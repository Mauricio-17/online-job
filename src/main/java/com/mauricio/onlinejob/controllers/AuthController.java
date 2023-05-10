package com.mauricio.onlinejob.controllers;

import com.mauricio.onlinejob.dto.UserDtoRequest;
import com.mauricio.onlinejob.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<UserService.AuthenticationResponse> register(@RequestBody  UserDtoRequest reqBody){
        return ResponseEntity.status(201).body(userService.registerUser(reqBody));
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<UserService.AuthenticationResponse> authenticate(@RequestBody UserService.AuthenticationRequest reqBody){
        return ResponseEntity.status(202).body(userService.authenticateUser(reqBody));
    }

}
