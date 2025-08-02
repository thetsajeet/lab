package com.app.ecommerce.dto;

import com.app.ecommerce.models.UserRole;

import lombok.Data;

@Data
public class UserRequest {
   private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDTO address;
}
