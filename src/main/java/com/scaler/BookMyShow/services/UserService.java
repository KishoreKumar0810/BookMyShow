package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.dtos.ChangePasswordDto;
import com.scaler.BookMyShow.dtos.UserDto;
import com.scaler.BookMyShow.exceptions.EmailNotFoundException;
import com.scaler.BookMyShow.exceptions.InvalidPasswordException;
import com.scaler.BookMyShow.exceptions.UserAlreadyExistsException;
import com.scaler.BookMyShow.exceptions.UserNotFoundException;
import com.scaler.BookMyShow.models.AuthProvider;
import com.scaler.BookMyShow.models.Role;
import com.scaler.BookMyShow.models.User;
import com.scaler.BookMyShow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailSender = mailSender;
    }

    @Transactional
    public User signUp(String name, String email, String password){
        /*
        1. Check if a user with email already exists
        2. If yes, log them in
        3. Else create a new user with an encoded password
        4. Save the user and return the user object
        * */
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            throw new UserAlreadyExistsException(email);
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setProvider(AuthProvider.LOCAL);
        user.setRole(Set.of(Role.USER));

        // generate token
        String token = UUID.randomUUID().toString();
        user.setVerifiedToken(token);
        user.setEmailVerified(false);

        userRepository.save(user);

        sendVerificationEmail(user);
        return user;
    }

    @Transactional
    public User logIn(String email, String password){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty())
            throw new EmailNotFoundException(email);

        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
            throw new InvalidPasswordException();

        return user;
    }

    private void sendVerificationEmail(User user){
        String subject = "Verfiy your email - BookMyShow";
        String verificationUrl = "http://localhost:9000/api/user/auth/verify-email?token=" + user.getVerifiedToken();
        String body = "Hi " + user.getName() + ",\nPlease click the" +
                " following link to verify your email address:\n" + verificationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public boolean verifyEmail(String token){
        Optional<User> userOptional = userRepository.findByVerifiedToken(token);
        if(userOptional.isEmpty()) return false;

        User user = userOptional.get();
        user.setEmailVerified(true);
        user.setVerifiedToken(null);
        userRepository.save(user);
        return true;
    }

    public UserDto getUserById(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException(userId);
        }
        return convertToDto(userOptional.get());
    }

    public UserDto getUserByEmail(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new EmailNotFoundException(email);
        }
        return convertToDto(userOptional.get());
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public String changePassword(Long id, ChangePasswordDto changePasswordDto){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException(id);
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())){
            throw new InvalidPasswordException();
        }
        user.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
        return "Password changed successfully";
    }

    public UserDto updateUser(Long id, UserDto userDto){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException(id);
        }
        User user = userOptional.get();
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        return convertToDto(user);
    }

    @Transactional
    public void deleteUser(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException(id);
        }
        userOptional.get().getRole().clear();

        userRepository.deleteById(id);
    }

    private UserDto convertToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
