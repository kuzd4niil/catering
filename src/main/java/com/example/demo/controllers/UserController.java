package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("page/{numberPage}")
    public List<User> getCaterings(@PathVariable(name = "numberPage") Integer numberPage,
                                       @RequestParam(name = "pageSize", defaultValue = "1") Short pageSize,
                                       @RequestParam(name = "sortDirection", defaultValue = "1") Short sortDirection,
                                       @RequestParam(name = "sortBy", defaultValue = "name") String sortBy
    ) {
        return userService.getUsers(
                numberPage,
                pageSize,
                sortDirection,
                sortBy
        );
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable(value = "id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable(value = "id") Long userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("{id}")
    public void removeUser(@PathVariable(value = "id") Long id) {
        userService.removeUser(id);
    }
}
