package com.gshop.userservice.controller;

import com.gshop.userservice.dto.UserDto;
import com.gshop.userservice.model.User;
import com.gshop.userservice.security.CustomUserDetails;
import com.gshop.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto.UserResponse> login(@RequestBody UserDto.LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }

    @PostMapping
    public ResponseEntity<UserDto.UserResponse> register(@RequestBody UserDto.RegisterRequest request) {
        return ResponseEntity.status(201).body(userService.registerUser(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto.UserResponse> getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userService.getUserProfile(userDetails.getId()));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDto.UserResponse> updateUserProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UserDto.UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateUserProfile(userDetails.getId(), request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto.UserResponse> updateUser(@PathVariable String id,
            @RequestBody UserDto.UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(java.util.Collections.singletonMap("message", "User removed"));
    }
}
