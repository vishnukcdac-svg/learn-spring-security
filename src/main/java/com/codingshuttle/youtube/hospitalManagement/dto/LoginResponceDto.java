package com.codingshuttle.youtube.hospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// this class is used to store the response of login request
@AllArgsConstructor
public class LoginResponceDto {
    String jwt;
    long userId;
}
