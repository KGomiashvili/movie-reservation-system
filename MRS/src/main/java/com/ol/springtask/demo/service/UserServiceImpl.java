package com.ol.springtask.demo.service;

import com.ol.springtask.demo.entity.User;
import com.ol.springtask.demo.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            Hibernate.initialize(result.get().getOccupiedSeats());
            return result.get();
        } else {
            throw new RuntimeException("Did not find user id: " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        List<User> allUsers =  userRepository.findAll();
        allUsers.forEach(user -> Hibernate.initialize(user.getOccupiedSeats()));
        return allUsers;
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("User with this name already exists.");
        }
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }
}
