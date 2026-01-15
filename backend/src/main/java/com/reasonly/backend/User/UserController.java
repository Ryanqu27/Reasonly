package com.reasonly.backend.User;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping()
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping("{id}/increment-streak")
    public void incrementStreak(@PathVariable Long id) {
        userService.incrementStreak(id);
    }
    
    @PutMapping("{id}/complete-today")
    public void updateCompletedDate(@PathVariable Long id) {
        userService.updateCompletedDate(id);
    }
    
}
