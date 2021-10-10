package com.tutorials.tutorialservice.services;

import com.tutorials.tutorialservice.exception.RunTimeExceptionPlaceHolder;
import com.tutorials.tutorialservice.models.Role;
import com.tutorials.tutorialservice.models.web.*;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {

    CreateUserResponse registerUser(SignUpRequest user) throws RunTimeExceptionPlaceHolder;
    String createUser(CreateUserRequest createUserRequest) throws RunTimeExceptionPlaceHolder;
    GetUserResponse getUserByUserId(String userId) throws RunTimeExceptionPlaceHolder;
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    GetUserResponse getUserInfo() throws RunTimeExceptionPlaceHolder;
    GetUserResponse getUserByUserName(String userName) throws RunTimeExceptionPlaceHolder;
    List<GetUserResponse> getAllUsers(Pageable pageable);
    void updateUserInfo(UpdateUserRequest updateUserRequest) throws RunTimeExceptionPlaceHolder;
    void deleteByUserId(String userId) throws RunTimeExceptionPlaceHolder;
    void updateUser(String userId, UpdateUserRequestFromAdmin requestFromAdmin) throws RunTimeExceptionPlaceHolder;
    JwtAuthenticationResponse authenticate(LoginRequest loginRequest) throws Exception;

}
