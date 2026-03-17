package com.codingshuttle.youtube.hospitalManagement.security;

import com.codingshuttle.youtube.hospitalManagement.dto.LoginRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.LoginResponceDto;
import com.codingshuttle.youtube.hospitalManagement.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServices {
    private final AuthenticationManager authenticationManager;

    private final AuthUtil authUtil;

    private final CustomUserDetailsService customUserDetailsService;
    public LoginResponceDto login(LoginRequestDto loginRequestDto) {

        // authenticate the user by username and password here we can used multiple authentication manager example jwt
        // here we are using username and password
        Authentication authentication=authenticationManager.authenticate(
                // here we are using username and password
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        // get the user details from authentication object
        // here we are using custom user details service
        // take the user details from authentication object by getPrincipal() method
        // get Principal() method return the user details from authentication object
        User user= (User) authentication.getPrincipal();

        // now we are generating the jwt token
        String jwtToken=authUtil.generateAccessToken(user);

        // return the jwt token and user id
        return new LoginResponceDto(jwtToken,user.getId());

    }
}
