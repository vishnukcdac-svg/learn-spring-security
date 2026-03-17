package com.codingshuttle.youtube.hospitalManagement.controller;

import com.codingshuttle.youtube.hospitalManagement.dto.LoginRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.LoginResponceDto;
import com.codingshuttle.youtube.hospitalManagement.security.AuthServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthServices authServices;

    @PostMapping("/login")
    public ResponseEntity<LoginResponceDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        return ResponseEntity.ok(authServices.login(loginRequestDto));
    }
}
