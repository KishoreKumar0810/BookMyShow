package com.scaler.BookMyShow.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

// This class ensures that every incoming request to the server is authenticated using a valid JWT — not a session or cookie.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtProvider.validate(token)) {
                Claims claims = jwtProvider.getClaims(token);
                String subject = claims.getSubject();
                String email = claims.get("email", String.class);
                String role = claims.get("role", String.class);

                // Build authorities list using the role from jwt
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

                // Creating an authentication token
                UsernamePasswordAuthenticationToken authentication = new
                        UsernamePasswordAuthenticationToken(email, null, authorities);

                // Attach authentication to security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //  request moves to the controller if authenticated successfully
        filterChain.doFilter(request, response);
    }
}
