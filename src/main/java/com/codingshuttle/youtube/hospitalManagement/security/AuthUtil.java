package com.codingshuttle.youtube.hospitalManagement.security;

import com.codingshuttle.youtube.hospitalManagement.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// we make this class as component so that we can use it in other classes
// this class will be used to generate jwt token
// this class will be used to validate jwt token
@Component

public class AuthUtil {
    @Value("${jwt.secret}")
    private  String jwtSecretKey;


    // this method will return the secret key used for generating and validating jwt token
    private SecretKey getSecretKey() {
        // convert the secret key to SecretKey
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        // generate jwt token with user details and expiration time
        return
                Jwts.builder()
                        .setSubject(user.getUsername())// this is used to identify the user and it is unique
                        .claim("userId",user.getId().toString())// this is used to store user id in the token
                        .setIssuedAt(new Date())// this is used for set the token issue date and time
                        .setExpiration(new Date(new Date().getTime() + 1000 * 60 * 60)) // this is used for set the token expiration time  in this here 1000 * 60 * 60 is 1 hour
                        .signWith(getSecretKey())// this is used for set the secret key
                        .compact();// this is used for generate the token

    }
   // create method to get user name from token
    public String getUserNameFromToken(String token) {
//        Claims claims=Jwts.parser()
//                .verifyWith(getSecretKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getSubject();


                 return Jwts.parserBuilder()
                        .setSigningKey(getSecretKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
    }
}
