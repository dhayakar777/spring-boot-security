package com.tutorials.tutorialservice.services;

import com.tutorials.tutorialservice.models.User;
import com.tutorials.tutorialservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUserName(userName);
        if(!user.isPresent()) {
            log.error("User not found in the database");
            throw new RuntimeException("User not found!");
        } else {
            log.info("User found in the database");
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.get().getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            });
            return new org.springframework.security.core.userdetails.User(user.get().getUserName(),
                    user.get().getPassword(), authorities);
        }
    }
}
