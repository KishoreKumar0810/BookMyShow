package com.scaler.BookMyShow.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
        private String accessToken;
        private String refreshToken;
        private String tokenType = "Bearer";
        private Long userId;
        private String email;
        private String name;
}
