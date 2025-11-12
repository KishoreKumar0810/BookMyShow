package com.scaler.BookMyShow.security;

import com.scaler.BookMyShow.models.AuthProvider;
import com.scaler.BookMyShow.models.Role;
import com.scaler.BookMyShow.models.User;
import com.scaler.BookMyShow.repositories.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService { //This class loads user info from OAuth2 provider

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google/github
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        AuthProvider provider = switch (registrationId.toLowerCase()){
            case "google" -> AuthProvider.GOOGLE;
            case "github" -> AuthProvider.GITHUB;
            default -> AuthProvider.LOCAL;
        };

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (user.getProvider() != provider) {
                user.setProvider(provider);
                userRepository.save(user);
            }
        }
        else {
            user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setEmailVerified(true);
            user.setRole(Set.of(Role.USER));
            user.setProvider(provider);
            userRepository.save(user);
        }

        return oAuth2User;
    }
}
