package com.example.msusermanagement.Services;

import com.example.msusermanagement.Entities.AccountStatusStats;
import com.example.msusermanagement.Entities.User;
import com.example.msusermanagement.payload.request.EditProfileRequest;
import com.example.msusermanagement.payload.request.UpdatePasswordRequest;

import java.util.List;

public interface AccountService {

    List<User> listAccounts();

    void banAccount(String userName);

    void enableAccount(String userName);

    User getPrincipal(String userName);

    String editProfile(String userName, EditProfileRequest editProfileRequest);

    String updatePassword(String userName, UpdatePasswordRequest updatePasswordRequest);

}
