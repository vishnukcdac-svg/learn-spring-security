package com.codingshuttle.youtube.hospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// this class is used to store the response of login request
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponceDto {
    String jwt;
    long userId;
}
