package com.cristianorodrigues.authentication.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private String message;

    private UserNotFoundException(){}

    public UserNotFoundException(String message){
        super(message);
        this.message = message;
    }
}
