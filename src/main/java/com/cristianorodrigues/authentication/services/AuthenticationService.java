package com.cristianorodrigues.authentication.services;

import com.cristianorodrigues.authentication.controllers.dto.LoginUserDto;
import com.cristianorodrigues.authentication.controllers.dto.RegisterUserDto;
import com.cristianorodrigues.authentication.exceptions.UserNotFoundException;
import com.cristianorodrigues.authentication.models.User;
import com.cristianorodrigues.authentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public User authenticate(LoginUserDto login){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );

        return userRepository.findByEmail(login.getUsername()).orElseThrow();
    }

    public User signup(RegisterUserDto register){
        User user = User.builder()
                .email(register.getUsername())
                .password(passwordEncoder.encode(register.getPassword()))
                .roles(List.of(register.getRole())).build();

        return userRepository.save(user);
    }

    public User getInfo(String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("User not found."));
    }
}
