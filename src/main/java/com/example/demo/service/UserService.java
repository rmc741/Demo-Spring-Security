package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.DataIntegrityViolationException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user){
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DataIntegrityViolationException("User ja cadastrado!");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userRepository.save(user);
    }
}
