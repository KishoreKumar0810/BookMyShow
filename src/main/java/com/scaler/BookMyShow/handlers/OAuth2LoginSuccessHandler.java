package com.scaler.BookMyShow.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.BookMyShow.models.AuthProvider;
import com.scaler.BookMyShow.repositories.UserRepository;
import com.scaler.BookMyShow.security.JwtProvider;
import com.scaler.BookMyShow.security.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import com.scaler.BookMyShow.models.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public OAuth2LoginSuccessHandler(JwtProvider jwtProvider,
                                     UserRepository userRepository,
                                     RefreshTokenService refreshTokenService) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String providerName = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(a -> a.getAuthority().toLowerCase())
                .orElse("unknown");

        AuthProvider provider = providerName.contains("github") ? AuthProvider.GITHUB : AuthProvider.GOOGLE;

        // find or create a user
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setProvider(provider);
                    newUser.setEmailVerified(true);
                    return userRepository.save(newUser);
                });

        // Generate tokens
        String accessToken = jwtProvider.generateAccessToken(String.valueOf(user.getId()), user.getEmail(), user.getRole());
        String refreshToken = String.valueOf(refreshTokenService.createRefreshToken(user.getId()));

        // Return JSON response
        Map<String,Object> responseBody = new HashMap<>();
        responseBody.put("accessToken", accessToken);
        responseBody.put("refreshToken", refreshToken);
        responseBody.put("userId", user.getId());
        responseBody.put("email", user.getEmail());
        responseBody.put("name", user.getName());
        responseBody.put("expiresIn", 900); // 15 minutes in seconds

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), responseBody);
    }
}
