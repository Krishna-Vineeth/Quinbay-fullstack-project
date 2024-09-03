package com.example.user.javabackendproject.controllers;

import com.example.user.javabackendproject.ApiResponse;
import com.example.user.javabackendproject.dto.UserDto;
import com.example.user.javabackendproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public ApiResponse<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ApiResponse<>(true, "Users fetched successfully", users);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ApiResponse<UserDto> createUser(@RequestBody UserDto userDto) {
//        UserDto createdUser = userService.addUser(userDto);
//        return new ApiResponse<>(true, "User created successfully", createdUser);
//    }

    @GetMapping("/{id}")
    public ApiResponse<UserDto> getUserById(@PathVariable Long id) {
        Optional<UserDto> userDto = userService.getUserById(id);
        if (userDto.isPresent()) {
            return new ApiResponse<>(true, "User fetched successfully", userDto.get());
        } else {
            return new ApiResponse<>(false, "User not found");
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ApiResponse<>(true, "User deleted successfully");
    }
}
