package com.goutham.jwtSpring.service;


import com.goutham.jwtSpring.dto.LoginRequest;
import com.goutham.jwtSpring.dto.RegisterRequest;
import com.goutham.jwtSpring.entity.Role;
import com.goutham.jwtSpring.entity.User;
import com.goutham.jwtSpring.repository.RoleRepository;
import com.goutham.jwtSpring.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User signup(RegisterRequest input) {

        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        Role userRole = roleRepository.findByName(Role.RoleName.valueOf(input.getRole()))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User()
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()))
//                .setPassword(input.getPassword())
                .setRoles(Set.of(userRole));


        return userRepository.save(user);
    }

    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

}
