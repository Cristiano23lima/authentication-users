package com.cristianorodrigues.authentication.controllers.dto;

import com.cristianorodrigues.authentication.models.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    private String username;
    private String password;
    private RoleName role;
}
