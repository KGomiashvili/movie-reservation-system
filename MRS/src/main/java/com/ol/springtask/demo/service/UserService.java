package com.ol.springtask.demo.service;

import com.ol.springtask.demo.entity.User;
import java.util.List;

public interface UserService{
    User getUserById(Integer id);
    List<User> getAllUsers();
    User save(User user);
    void deleteUser(Integer id);
    User findByUsername(String username);
    boolean existsById(Integer id);
}
