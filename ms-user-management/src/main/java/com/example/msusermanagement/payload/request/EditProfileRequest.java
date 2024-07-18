package com.example.msusermanagement.payload.request;

import lombok.Data;

@Data
public class EditProfileRequest {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
}
