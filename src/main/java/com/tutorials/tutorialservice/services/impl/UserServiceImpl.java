package com.tutorials.tutorialservice.services.impl;

import com.tutorials.tutorialservice.exception.CustomException;
import com.tutorials.tutorialservice.exception.CustomExceptionResponse;
import com.tutorials.tutorialservice.exception.RunTimeExceptionPlaceHolder;
import com.tutorials.tutorialservice.exception.SuccessCodeWithErrorResponse;
import com.tutorials.tutorialservice.models.Role;
import com.tutorials.tutorialservice.models.User;
import com.tutorials.tutorialservice.models.web.*;
import com.tutorials.tutorialservice.repository.RoleRepository;
import com.tutorials.tutorialservice.repository.RolesRepository;
import com.tutorials.tutorialservice.repository.UserRepository;
import com.tutorials.tutorialservice.services.UserRoleService;
import com.tutorials.tutorialservice.services.UserService;
import com.tutorials.tutorialservice.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.tutorials.tutorialservice.models.web.Roles.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserRoleService userRoleService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RolesRepository userRolesRepository;

    @Override
    public CreateUserResponse registerUser(SignUpRequest signUpRequest) throws RunTimeExceptionPlaceHolder {

        if(userRepository.existsByUserName(signUpRequest.getUserName())) {
            log.error("Duplicate user name {}", signUpRequest.getUserName());
            throw new RunTimeExceptionPlaceHolder("User name is already taken, please choose a different user name!");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            log.error("Duplicate email {}", signUpRequest.getEmail());
            throw new RunTimeExceptionPlaceHolder("Email address is already in use, please choose a unique email address!");
        }
        String encodedPassword = encoder.encode(signUpRequest.getPassword());
        User newUser = User.builder().userName(signUpRequest.getUserName()).firstName(signUpRequest.getFirstName())
                      .lastName(signUpRequest.getLastName()).email(signUpRequest.getEmail()).password(encodedPassword) .build();
        Role userRole = roleRepository.findByRoleName("ROLE_USER")
                        .orElseThrow(() -> new RunTimeExceptionPlaceHolder("User role not set."));
        newUser.setRoles(Collections.singletonList(userRole));
        log.info("Creating the new user {}", newUser);
        User savedUser = userRepository.save(newUser);
        return CreateUserResponse.builder().userId(savedUser.getUserId()).
                userName(savedUser.getUserName()).build();
    }

    @Override
    public String createUser(CreateUserRequest createUserRequest) throws RunTimeExceptionPlaceHolder {
        String encodedPassword = encoder.encode(createUserRequest.getPassword());
        if(userRepository.existsByUserName(createUserRequest.getUserName())) {
            throw new RunTimeExceptionPlaceHolder("User name already taken, choose a different user name");
        }
        if(userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new RunTimeExceptionPlaceHolder("Email address is already in use, choose a different email address");
        }
        CustomExceptionResponse exceptionResponse = CustomExceptionResponse.builder()
                                                    .uuid(UUID.randomUUID()).exceptionList(new ArrayList<>()).build();
        List<Role> validRoles = new ArrayList<>();
        createUserRequest.getRoleNames().forEach(roleName-> {
           // If role exist in the DB persist, else add exception response and persist valid roles send response containing
            // invalid roles
           userRolesRepository.findByUserRole(roleName).get(0).<Runnable>map(role -> () -> {
               Role role1 = Role.builder().roleName(role.getUserRole()).build();
               Roles enumRole = Roles.valueOf(role.getUserRole());
               switch (enumRole) {
                   case ROLE_USER:
                        role1.setRoleDescription("Role User");
                        break;
                   case ROLE_ADMIN:
                        role1.setRoleDescription("Role Admin");
                        break;
                   case ROLE_MANAGER:
                        role1.setRoleDescription("Role Manager");
                        break;
                   case ROLE_SUPER_ADMIN:
                       role1.setRoleDescription("Role Super Admin");
                       break;
                   default:
                       role1.setRoleDescription("Default user");
               }
               validRoles.add(role1);
           })
            .orElse(()-> {
                CustomException exception = CustomException.builder()
                           .code("400").message(roleName +" role doesn't exist").build();
                exceptionResponse.getExceptionList().add(exception);
            }).run();
        });
        User user = User.builder().userName(createUserRequest.getUserName()).firstName(createUserRequest.getFirstName())
                    .lastName(createUserRequest.getLastName()).email(createUserRequest.getEmail()).password(encodedPassword)
                    .roles(new ArrayList<>(validRoles)).build();
        validRoles.forEach(user::addRole);
        User savedUser = userRepository.save(user);
        if(!exceptionResponse.getExceptionList().isEmpty()) {
            throw new SuccessCodeWithErrorResponse(savedUser.getUserId(), exceptionResponse);
        }
        return savedUser.getUserId();
    }

    @Override
    public GetUserResponse getUserByUserId(String userId) throws RunTimeExceptionPlaceHolder {
      Optional<User> optionalUser = userRepository.findByUserName(userId);
      User currentUser = optionalUser.orElseThrow(()-> new RunTimeExceptionPlaceHolder("User not found"));
      return GetUserResponse.builder().userId(currentUser.getUserId())
              .firstName(currentUser.getFirstName()).lastName(currentUser.getLastName())
              .userName(currentUser.getUserName()).email(currentUser.getEmail()).
                      roles(currentUser.getRoles()).build();
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Creating the new role {}", role);
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        Optional<User> availableUser = userRepository.findByUserName(userName);
        Optional<Role> existingRole = roleRepository.findByRoleName(roleName);
        availableUser.ifPresent(user -> user.getRoles().add(existingRole.get()));
        log.info("Role {} has been successfully added to the user {}", roleName, availableUser.get());
    }

    @Override
    public GetUserResponse getUserInfo() throws RunTimeExceptionPlaceHolder {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        Optional<User> existingUser = userRepository.findByUserName(userName);
       if(existingUser.isPresent()) {
           log.info("User found with the given user name {}", userName);
           return  GetUserResponse.builder().userId(existingUser.get().getUserId())
                                          .userName(existingUser.get().getUserName())
                                          .firstName(existingUser.get().getFirstName())
                                          .lastName(existingUser.get().getLastName())
                                          .email(existingUser.get().getEmail())
                                          .roles(existingUser.get().getRoles()).build();

       } else {
           log.error("No user found with user name {} ", userName);
           throw new RunTimeExceptionPlaceHolder("User not found with the given user name");
       }
    }

    @Override
    public GetUserResponse getUserByUserName(String userName) throws RunTimeExceptionPlaceHolder {
      Optional<User> optionalUser = userRepository.findByUserName(userName);
      User user = optionalUser.orElseThrow(()-> new RunTimeExceptionPlaceHolder("User not found"));
      return GetUserResponse.builder().userId(user.getUserId()).userName(user.getUserName())
               .firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail())
               .roles(user.getRoles()).build();
    }

    @Override
    public List<GetUserResponse> getAllUsers(Pageable pageable) {
        log.info("Fetching all the users in paginated format");
        Iterable<User> all ;
       /* if(page == 0 && size == 0) {
             Pageable defaultPaging = PageRequest.of(0, 10);
             all = userRepository.findAll(defaultPaging);
        } else {*/
           // Pageable paging = PageRequest.of(page.);
            all = userRepository.findAll(pageable);
        /*}*/
        List<GetUserResponse> userResponseList = new ArrayList<>();
        all.iterator().forEachRemaining(user -> {
            GetUserResponse response = GetUserResponse.builder().userId(user.getUserId())
                                .userName(user.getUserName()).firstName(user.getFirstName())
                                .lastName(user.getLastName()).email(user.getEmail()).roles(user.getRoles())
                                 .build();
            userResponseList.add(response);
        });
        return userResponseList;
    }

    @Override
    public void updateUserInfo(UpdateUserRequest updateUserRequest) throws RunTimeExceptionPlaceHolder {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = (String) authentication.getPrincipal();
      Optional<User> optionalUser = userRepository.findByUserName(userName);
      User userByUserName = optionalUser.orElseThrow(()-> new
              RunTimeExceptionPlaceHolder("User name or email doesn't exist"));
      if(updateUserRequest.getFirstName() != null) {
          userByUserName.setFirstName(updateUserRequest.getFirstName());
      }
      if (updateUserRequest.getLastName() != null) {
          userByUserName.setLastName(updateUserRequest.getLastName());
      }
      if(updateUserRequest.getEmail() != null) {
          userByUserName.setEmail(updateUserRequest.getEmail());
      }
      if (updateUserRequest.getPassword() != null) {
          userByUserName.setPassword(encoder.encode(updateUserRequest.getPassword()));
      }
      userRepository.save(userByUserName);
    }

    @Override
    public void deleteByUserId(String userId) throws RunTimeExceptionPlaceHolder {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        String availableUserId = getUserByUserId(userId).getUserId();
        if(userName.equals(availableUserId)) {
          throw new RunTimeExceptionPlaceHolder("You cannot delete your own account");
        }
    }

    @Override
    public void updateUser(String userId, UpdateUserRequestFromAdmin requestFromAdmin) throws RunTimeExceptionPlaceHolder {
        Optional<User> existingUser = userRepository.findByUserName(userId);
        User user = existingUser.orElseThrow(()-> new RunTimeExceptionPlaceHolder("User id doesn't exist"));
        if(requestFromAdmin.getFirstName() != null) {
            user.setFirstName(user.getFirstName());
        }
        if (requestFromAdmin.getLastName() != null) {
            user.setLastName(requestFromAdmin.getLastName());
        }
        if(requestFromAdmin.getEmail() != null) {
            user.setEmail(requestFromAdmin.getEmail());
        }
        if(user.getRoles().size() > 0) {
          MapUserToRolesRequest mapUserToRolesRequest = new MapUserToRolesRequest();
          List<String> existingRoles = user.getRoles().stream().map(Role::getRoleName).
                  collect(Collectors.toList());
          mapUserToRolesRequest.setRoleNames(existingRoles);
          userRoleService.removeRolesFromUser(user.getUserName(), mapUserToRolesRequest);
        }
        if(requestFromAdmin.getRoles().size() > 0) {
            MapUserToRolesRequest mapUserToRolesRequest = new MapUserToRolesRequest();
            mapUserToRolesRequest.setRoleNames(requestFromAdmin.getRoles());
            userRoleService.mapUserToRoles(user.getUserName(), mapUserToRolesRequest);
        }
    }

    @Override
    public JwtAuthenticationResponse authenticate(LoginRequest loginRequest) throws Exception {
        if(loginRequest.getUserName()!=null && loginRequest.getPassword() != null) {
            Optional<User> optionalUser = userRepository.findByUserName(loginRequest.getUserName());
            User user = optionalUser.orElseThrow(()-> new RunTimeExceptionPlaceHolder("User not found in the DB"));
            boolean passwordMatches = encoder.matches(loginRequest.getPassword(), user.getPassword());
            if(!passwordMatches) {
                log.error("Passwords are not matching ");
                throw new RunTimeExceptionPlaceHolder("Passwords do not match, Please enter a correct password");
            }
            setAuthentication(loginRequest);
            return jwtTokenUtil.generateToken(loginRequest);
        } else {
            log.error("User name or password is null");
            throw new RunTimeExceptionPlaceHolder("User name or password is required");
        }
    }
    private void setAuthentication(LoginRequest loginRequest) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                    loginRequest.getPassword()));
        } catch (DisabledException e) {
            log.error("Exception occurred");
            throw new RunTimeExceptionPlaceHolder("User disabled");
        } catch (BadCredentialsException e) {
            log.error("Bad credentials");
            throw new RunTimeExceptionPlaceHolder("Bad credentials");
        }
    }
}
