package com.example.itssd.controller;

import com.example.itssd.entity.User;
import com.example.itssd.repository.UserRepository;
import com.example.itssd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itss/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getProfile")
    public User login() {
        return userService.logIn();
    }

    @GetMapping("/logout")
    public void logout(@RequestParam Long userId) {
        userService.logOut(userId);
    }

    @GetMapping("/getFriends")
    public List<User> getFriends(@RequestParam Long userId) {
        return userService.getAllFriends(userId);
    }

    @GetMapping("/getAllMember")
    public List<User> getAllMember(@RequestParam Long userId) {
        return userService.getRecommendFriends(userId);
    }

    @GetMapping("/loginById")
    public User loginById(@RequestParam Long id) {
        return userService.getUserById(id);
    }

}
