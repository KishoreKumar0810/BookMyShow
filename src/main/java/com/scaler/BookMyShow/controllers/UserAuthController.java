package com.scaler.BookMyShow.controllers;

import com.scaler.BookMyShow.dtos.*;
import com.scaler.BookMyShow.models.ResponseStatus;
import com.scaler.BookMyShow.models.RefreshToken;
import com.scaler.BookMyShow.models.User;
import com.scaler.BookMyShow.security.JwtProvider;
import com.scaler.BookMyShow.security.RefreshTokenService;
import com.scaler.BookMyShow.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/auth")
public class UserAuthController {
    private UserService userService;
    private JwtProvider jwtProvider;
    private RefreshTokenService refreshTokenService;

    public UserAuthController(UserService userService, JwtProvider jwtProvider, RefreshTokenService refreshTokenService){
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();

        User user = userService.signUp(
                signUpRequestDto.getName(),
                signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword()
        );

        signUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        signUpResponseDto.setUserId(user.getId());

        return signUpResponseDto;
    }

    @GetMapping("/verify-email")
    public boolean verifyEmail(@RequestParam String token){
        return userService.verifyEmail(token);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        User user = userService.logIn(request.getEmail(), request.getPassword());

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

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDto> refreshToken(@RequestBody TokenRefreshRequestDto request) {
        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        User user = refreshToken.getUser();

        String newAccessToken = jwtProvider.generateAccessToken(String.valueOf(user.getId()), user.getEmail(), user.getRole());

        LoginResponseDto response = new LoginResponseDto();
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(requestRefreshToken);
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestBody LogoutRequestDto request) {
        refreshTokenService.deleteByUserId(request.getUserId());
        return ResponseEntity.ok("User logged out successfully!");
    }
}
