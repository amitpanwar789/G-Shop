package com.gshop.userservice.service;

import com.gshop.userservice.dto.UserDto;
import com.gshop.userservice.model.User;

import java.util.List;

public interface UserService {
    UserDto.UserResponse registerUser(UserDto.RegisterRequest request);

    UserDto.UserResponse loginUser(UserDto.LoginRequest request);

    UserDto.UserResponse getUserProfile(String userId);

    UserDto.UserResponse updateUserProfile(String userId, UserDto.UpdateProfileRequest request);

    List<User> getUsers();

    void deleteUser(String id);

    User getUserById(String id);

    UserDto.UserResponse updateUser(String id, UserDto.UpdateProfileRequest request); // Admin update
}
