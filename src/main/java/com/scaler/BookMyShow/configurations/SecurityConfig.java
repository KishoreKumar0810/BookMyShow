package com.scaler.BookMyShow.configurations;

import com.scaler.BookMyShow.handlers.OAuth2LoginSuccessHandler;
import com.scaler.BookMyShow.repositories.UserRepository;
import com.scaler.BookMyShow.security.CustomOAuth2UserService;
import com.scaler.BookMyShow.security.JwtAuthenticationFilter;
import com.scaler.BookMyShow.security.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final OAuth2LoginSuccessHandler successHandler;

    public SecurityConfig(JwtProvider jwtProvider,
                          OAuth2LoginSuccessHandler successHandler) {
        this.jwtProvider = jwtProvider;
        this.successHandler = successHandler;
    }

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {
    var jwtFilter = new JwtAuthenticationFilter(jwtProvider);

    http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    // Permit public endpoints
                    .requestMatchers(
                            "/api/admin/**",          // includes register, verify, refresh, etc.
                            "/api/users/auth/**",
                            "/oauth2/**", "/login", "/error"
                    ).permitAll()

                    // only users can access these endpoints
                    .requestMatchers("/api/user/auth/**").hasRole("USER")

                    // only theater owners can access these endpoints
                    .requestMatchers("/api/TO/**").hasRole("THEATER_OWNER")

                    // only admins can access these endpoints
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")

                    // all other requests need to be authenticated
                    .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                    .successHandler(successHandler)
                    .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
            )
            .formLogin(AbstractHttpConfigurer::disable);

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
    }
}
