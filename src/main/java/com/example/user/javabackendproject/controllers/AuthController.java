package com.example.user.javabackendproject.controllers;

import com.example.user.javabackendproject.ApiResponse;
import com.example.user.javabackendproject.dto.LoginDto;
import com.example.user.javabackendproject.dto.SignUpDto;
import com.example.user.javabackendproject.dto.UserDto;
import com.example.user.javabackendproject.services.AuthService;
import com.example.user.javabackendproject.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @CrossOrigin
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }
//    @CrossOrigin
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request,
//                                        HttpServletResponse response) {
//        String token = authService.login(loginDto);
//
//        Cookie cookie = new Cookie("AuthToken", token);
//        cookie.setHttpOnly(true);
//        response.addCookie(cookie);
//
//        return ResponseEntity.ok(token);
//    }
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDto loginDto, HttpServletRequest request,
                                                     HttpServletResponse response) {
        try {
            String token = authService.login(loginDto);

            Cookie cookie = new Cookie("AuthToken", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);

            ApiResponse<String> apiResponse = new ApiResponse<>(true, "Login successful", token);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(false, "Login failed: " + e.getMessage(), null);
            return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
        }
    }
}
