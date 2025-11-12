package com.scaler.BookMyShow.controllers;

import com.scaler.BookMyShow.dtos.ChangePasswordDto;
import com.scaler.BookMyShow.dtos.UserDto;
import com.scaler.BookMyShow.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("email") String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/changepassword/{id}")
    public ResponseEntity<String>changePassword(@PathVariable("id") Long id, ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(userService.changePassword(id, changePasswordDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
