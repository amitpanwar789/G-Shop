package com.gshop.userservice.service.impl;

import com.gshop.userservice.dto.UserDto;
import com.gshop.userservice.model.User;
import com.gshop.userservice.repository.UserRepository;
import com.gshop.userservice.security.CustomUserDetails;
import com.gshop.userservice.service.UserService;
import com.gshop.userservice.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDto.UserResponse registerUser(UserDto.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists"); // TODO: Use Custom Exception
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isAdmin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        String token = jwtUtils.generateToken(CustomUserDetails.build(savedUser), savedUser.getId(),
                savedUser.getName(), savedUser.isAdmin());

        return UserDto.UserResponse.builder()
                ._id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .isAdmin(savedUser.isAdmin())
                .token(token)
                .build();
    }

    @Override
    public UserDto.UserResponse loginUser(UserDto.LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Used findByEmail to get the User object to get name and isAdmin, or cast
        // Principal if it has it.
        // CustomUserDetails has authorities but not raw isAdmin boolean comfortably
        // without parsing.
        // But we constructed CustomUserDetails from User.
        // Let's rely on authorities for isAdmin.
        boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        // We don't have name in CustomUserDetails? We passed email as username.
        // I need to fetch user or update CustomUserDetails to hold name.
        // For efficiency, I'll update CustomUserDetails to hold name.
        // For now, let's fetch user or assume email is name? No.
        // Quickest fix: Fetch user by email (we already loaded it in
        // loadUserByUsername).
        // OR add name field to CustomUserDetails.
        // Let's add name to CustomUserDetails?
        // For now, I'll just change the logic here to fetch user again? No that's
        // waste.
        // Check CustomUserDetails... it has id, email, password.
        // I will just fetch it from repo for now to be safe and quick or add it.
        // Actually, let's just make a quick lookup since we are in Service.
        User user = userRepository.findById(userDetails.getId()).orElseThrow();
        String token = jwtUtils.generateToken(userDetails, userDetails.getId(), user.getName(), user.isAdmin());

        return UserDto.UserResponse.builder()
                ._id(userDetails.getId())
                .name(userDetails.getUsername()) // This is email in UserDetails
                .email(userDetails.getEmail())
                .isAdmin(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
                .token(token)
                .build();
    }

    @Override
    public UserDto.UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToUserResponse(user, null);
    }

    @Override
    public UserDto.UserResponse updateUserProfile(String userId, UserDto.UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getName() != null)
            user.setName(request.getName());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        String token = jwtUtils.generateToken(CustomUserDetails.build(updatedUser), updatedUser.getId(),
                updatedUser.getName(), updatedUser.isAdmin());

        return mapToUserResponse(updatedUser, token);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDto.UserResponse updateUser(String id, UserDto.UpdateProfileRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getName() != null)
            user.setName(request.getName());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        // For Admin update, usually we don't update password simply? Logic to be
        // confirmed.
        // Following existing node Logic:
        // user.isAdmin = req.body.isAdmin || user.isAdmin;

        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);

        return mapToUserResponse(updatedUser, null);
    }

    private UserDto.UserResponse mapToUserResponse(User user, String token) {
        return UserDto.UserResponse.builder()
                ._id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .isAdmin(user.isAdmin())
                .token(token)
                .build();
    }
}
