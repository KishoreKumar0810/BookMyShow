package com.scaler.BookMyShow.controllers;

import com.scaler.BookMyShow.dtos.LoginRequestDto;
import com.scaler.BookMyShow.dtos.LoginResponseDto;
import com.scaler.BookMyShow.dtos.SignUpRequestDto;
import com.scaler.BookMyShow.dtos.SignUpResponseDto;
import com.scaler.BookMyShow.models.ResponseStatus;
import com.scaler.BookMyShow.models.User;
import com.scaler.BookMyShow.security.JwtProvider;
import com.scaler.BookMyShow.security.RefreshTokenService;
import com.scaler.BookMyShow.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public AdminController(UserService userService, JwtProvider jwtProvider, RefreshTokenService refreshTokenService){
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();

        User user = userService.adminSignUp(
                signUpRequestDto.getName(),
                signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword()
        );

        signUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        signUpResponseDto.setUserId(user.getId());

        return signUpResponseDto;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> adminLogin(@RequestBody LoginRequestDto request) {
        User user = userService.adminLogIn(request.getEmail(), request.getPassword());

        // Generate JWTs
        String accessToken = jwtProvider.generateAccessToken(String.valueOf(user.getId()), user.getEmail(), user.getRole());
        String refreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();

        LoginResponseDto response = new LoginResponseDto();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());

        return ResponseEntity.ok(response);
    }

}
