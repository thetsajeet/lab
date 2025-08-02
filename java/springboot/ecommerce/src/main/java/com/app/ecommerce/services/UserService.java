package com.app.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecommerce.models.User;
import com.app.ecommerce.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    // error
    public boolean updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
}
