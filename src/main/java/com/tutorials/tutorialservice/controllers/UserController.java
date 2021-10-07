package com.tutorials.tutorialservice.controllers;


import com.tutorials.tutorialservice.exception.RunTimeExceptionPlaceHolder;
import com.tutorials.tutorialservice.models.web.*;
import com.tutorials.tutorialservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/all-users", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        List<GetUserResponse> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) throws RunTimeExceptionPlaceHolder {
        String userId = userService.createUser(createUserRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}")
                       .buildAndExpand(userId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<GetUserResponse> getUser(@RequestParam("userName") Optional<String> userName
                                         , @RequestParam("userId") Optional<String> userId) throws RunTimeExceptionPlaceHolder {
        GetUserResponse userResponse = new GetUserResponse();
        if(userName.isPresent()) {
           userResponse = userService.getUserByUserName(userName.get());
        } else if(userId.isPresent()) {
            userResponse = userService.getUserByUserName(userId.get());
        }
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<GetUserResponse> getUserInfo() throws RunTimeExceptionPlaceHolder {
        GetUserResponse userResponse = userService.getUserInfo();
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable("userId") String userId,
                                        @RequestBody @Valid UpdateUserRequestFromAdmin requestFromAdmin) throws RunTimeExceptionPlaceHolder {
     userService.updateUser(userId, requestFromAdmin);
     return ResponseEntity.ok().build();
    }

    @PutMapping("/userInfo")
    public ResponseEntity<?> updateUserInfo(@RequestBody @Valid  UpdateUserRequest updateUserRequest) throws RunTimeExceptionPlaceHolder {
      userService.updateUserInfo(updateUserRequest);
      return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) throws RunTimeExceptionPlaceHolder {
        userService.deleteByUserId(userId);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
