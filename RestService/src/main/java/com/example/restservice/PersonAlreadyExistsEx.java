package com.example.restservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PersonAlreadyExistsEx extends RuntimeException {
    public PersonAlreadyExistsEx() {
        super("The specified person already exists");
    }
}
