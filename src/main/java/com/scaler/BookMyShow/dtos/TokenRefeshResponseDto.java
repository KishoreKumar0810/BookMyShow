package com.scaler.BookMyShow.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefeshResponseDto {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private String error;
}