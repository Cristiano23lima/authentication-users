package com.cristianorodrigues.authentication.controllers;

import com.cristianorodrigues.authentication.controllers.dto.LoginUserDto;
import com.cristianorodrigues.authentication.controllers.dto.RegisterUserDto;
import com.cristianorodrigues.authentication.controllers.response.LoginResponse;
import com.cristianorodrigues.authentication.models.User;
import com.cristianorodrigues.authentication.models.UserDetailsImpl;
import com.cristianorodrigues.authentication.services.AuthenticationService;
import com.cristianorodrigues.authentication.services.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginUserDto form
            ){
        User authenticatedUser = authenticationService.authenticate(form);

        String jwtToken = jwtService.generateToken(new UserDetailsImpl(authenticatedUser));

        LoginResponse loginResponse = LoginResponse.builder().token(jwtToken).expireIn(jwtService.getExpirationTime()).build();

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto){
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.created(URI.create("https://nextronix.com.br/api/v1/user/register")).body(registeredUser);
    }

    @GetMapping("/info")
    public ResponseEntity<User> info(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        return ResponseEntity.ok(authenticationService.getInfo(principal.getName()));
    }

}
