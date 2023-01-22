package com.driver.controller;

import com.driver.models.Author;
import com.driver.services.AuthorService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//Add required annotations
@RestController
public class AuthorController {

    @Autowired
    AuthorService authorservice;

    //Write createAuthor API with required annotations
    @PostMapping("/author")
    public ResponseEntity<?> createAuthor(@RequestBody Author author ){
        this.authorservice.create(author);
        return new ResponseEntity<>("Success", HttpStatus.CREATED );
    }
}
