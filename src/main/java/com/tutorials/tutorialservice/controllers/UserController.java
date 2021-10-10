package com.tutorials.tutorialservice.controllers;


import com.tutorials.tutorialservice.exception.RunTimeExceptionPlaceHolder;
import com.tutorials.tutorialservice.models.web.*;
import com.tutorials.tutorialservice.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@Api(tags = "Users Controller")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/all-users", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "View list of registered users as a admin")
    public ResponseEntity<List<GetUserResponse>> getAllUsers(@ApiParam(name = "Paged info", value =
            "Paging Parameters to retrieve list of users in paginated format ") @PageableDefault Pageable page) {
        List<GetUserResponse> allUsers = userService.getAllUsers(page);
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @ApiOperation(value = "Create a new user as a admin")
    public ResponseEntity<?> createUser(@RequestBody @Valid @ApiParam(name = "createUserRequest", required = true, value = "Create a new user") CreateUserRequest createUserRequest) throws RunTimeExceptionPlaceHolder {
        String userId = userService.createUser(createUserRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}")
                       .buildAndExpand(userId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve a user information")
    public ResponseEntity<GetUserResponse> getUser(@ApiParam(name = "userName", required = true) @RequestParam("userName") Optional<String> userName
                                         , @ApiParam(name = "userId", required = true) @RequestParam("userId") Optional<String> userId) throws RunTimeExceptionPlaceHolder {
        GetUserResponse userResponse = new GetUserResponse();
        if(userName.isPresent()) {
           userResponse = userService.getUserByUserName(userName.get());
        } else if(userId.isPresent()) {
            userResponse = userService.getUserByUserName(userId.get());
        }
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping(value = "/userInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get current logged in user information")
    public ResponseEntity<GetUserResponse> getUserInfo() throws RunTimeExceptionPlaceHolder {
        GetUserResponse userResponse = userService.getUserInfo();
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Update a user information with User Id from admin")
    public ResponseEntity<?> updateUser(@ApiParam(name = "userId", required = true) @PathVariable("userId") String userId,
                                        @ApiParam(name = "requestFromAdmin", required = true,value = "Update user as admin") @RequestBody @Valid UpdateUserRequestFromAdmin requestFromAdmin) throws RunTimeExceptionPlaceHolder {
     userService.updateUser(userId, requestFromAdmin);
     return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/userInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update current logged in user information")
    public ResponseEntity<?> updateUserInfo(@RequestBody @Valid @ApiParam(name = "updateUserRequest", required = true, value = "Update user info as logged in user")  UpdateUserRequest updateUserRequest) throws RunTimeExceptionPlaceHolder {
      userService.updateUserInfo(updateUserRequest);
      return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Delete a user with user Id as admin")
    public ResponseEntity<?> deleteUser(@ApiParam(name = "userId", required = true) @PathVariable("userId") String userId) throws RunTimeExceptionPlaceHolder {
        userService.deleteByUserId(userId);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
