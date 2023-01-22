package com.driver.controller;

//Add required annotations
@RestController
public class AuthorController {

    @Autowired
    AuthorService authorservice;

    //Write createAuthor API with required annotations
    @PostMapping("/author")
    public ResponseEntity<?> createAuthor(@RequestBody Author author ){
        this.authorservice.create(author);
        return new ResponseEntity<>("Success",HttpStatus.CREATED );
    }
}
