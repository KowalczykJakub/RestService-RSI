package com.example.restservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PersonIdAlreadyExistsEx extends RuntimeException {
    public PersonIdAlreadyExistsEx() {
        super("The specified person id is already used");
    }
    public PersonIdAlreadyExistsEx(int id) {
        super(String.valueOf(id));
    }
}
