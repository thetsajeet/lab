package com.app.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecommerce.dto.AddressDTO;
import com.app.ecommerce.dto.UserRequest;
import com.app.ecommerce.dto.UserResponse;
import com.app.ecommerce.models.Address;
import com.app.ecommerce.models.User;
import com.app.ecommerce.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    public void createUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        // user.setRole(userRequest.getRole());
        
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setZipCode(userRequest.getAddress().getZipCode());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        } else {
            user.setAddress(null); // Ensure address is null if not provided
        }
        
        userRepository.save(user);
    }

    public Optional<UserResponse> getUser(Long userId) {
        return userRepository.findById(userId).map(this::mapToUserResponse);
    }

    public boolean updateUser(Long userId, UserRequest updatedUserRequest) {
        return userRepository.findById(userId)
                .map(user -> {
                    updateUserFromRequest(user, updatedUserRequest);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId().toString());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());    
            addressDTO.setZipCode(user.getAddress().getZipCode());
            addressDTO.setCountry(user.getAddress().getCountry());
            response.setAddress(addressDTO);      
        }   
        return response;
    }
}
