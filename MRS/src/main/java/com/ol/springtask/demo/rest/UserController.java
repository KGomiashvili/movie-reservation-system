package com.ol.springtask.demo.rest;

import com.ol.springtask.demo.entity.User;
import com.ol.springtask.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAllUsers() {
        //regular users can view only their data
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return userService.getAllUsers();
        } else {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            return List.of(userService.getUserById(user.getId()));
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return userService.getUserById(id);
        } else {
            String username = authentication.getName();
            User user = userService.findByUsername(username);

            if (user != null && user.getId().equals(id)) {
                return userService.getUserById(id);
            } else {
                throw new RuntimeException("Access Denied: You can only access your own details.");
            }
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        user.setId(0);
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            User user = userService.getUserById(id);

            if (user == null) {
                throw new RuntimeException("User id not found - " + id);
            }

            userService.deleteUser(id);
            return "Deleted user id - " + id;
        } else {
            throw new RuntimeException("Access Denied: You are not authorized to delete users.");
        }
    }

    @GetMapping("/search")
    public User getUserByUsername(@RequestParam String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new RuntimeException("User not found with username - " + username);
            }

            return user;
        } else {
            throw new RuntimeException("Access Denied: You are not authorized to search for users.");
        }
    }
}
