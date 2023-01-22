package com.driver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Add required annotations
@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionservice;

    //Add required annotations
    @PostMapping("/transaction/issueBook")
    public ResponseEntity issueBook(@RequestParam("cardId") int cardId, @RequestParam("bookId") int bookId) throws Exception{
        this.transactionservice.issueBook( cardId, bookId );
       return new ResponseEntity<>("transaction completed", HttpStatus.ACCEPTED);
    }

    //Add required annotations
    @PostMapping("/transaction/returnBook ")
    public ResponseEntity returnBook(@RequestParam("cardId") int cardId, @RequestParam("bookId") int bookId) throws Exception{
        this.transactionservice.returnBook( cardId, bookId );
        return new ResponseEntity<>("transaction completed", HttpStatus.ACCEPTED);
    }
}
