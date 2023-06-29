package com.auth.ken.authjwt.controller;

import com.auth.ken.authjwt.dto.UserResponse;
import com.auth.ken.authjwt.dto.VideoDTO;
import com.auth.ken.authjwt.model.User;
import com.auth.ken.authjwt.repository.UserRepository;
import com.auth.ken.authjwt.service.UserRegisterService;
import com.auth.ken.authjwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRegisterService userRegisterService;
    private final UserService userService;

    private final UserRepository userRepository;

    @GetMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse register(Authentication authentication){
        Jwt jwt = (Jwt) authentication.getPrincipal();
     return userRegisterService.registerUser(jwt.getTokenValue());
    }
    @PostMapping(value = "/subscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean subcribeUsers(@PathVariable String userId){
        userService.subscribeUser(userId);
        return true;
    }
    @GetMapping("all-users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllusers(){

        return (List<User>) userRepository.findAll();
    }
    @PostMapping(value = "/unsubscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean unsubcribeUsers(@PathVariable String userId){
        userService.unsubscribeUser(userId);
        return true;
    }

    @GetMapping(value = "/{userId}/history")
    public Set<String> userHistory(@PathVariable String userId){
       return userService.userHistory(userId);
    }
}
