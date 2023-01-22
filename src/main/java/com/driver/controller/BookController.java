package com.driver.controller;

import com.driver.models.Book;
import com.driver.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//Add required annotations
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    //Write createBook API with required annotations
    @PostMapping("/book")
    public ResponseEntity<?> createBook( @RequestBody Book book ){
        this.bookService.createBook( book );
        return new ResponseEntity<>("Success", HttpStatus.CREATED );
    }

    //Add required annotations
    @GetMapping("/book")
    public ResponseEntity getBooks(@RequestParam(value = "genre", required = false) String genre,
                                   @RequestParam(value = "available", required = false, defaultValue = "false") boolean available,
                                   @RequestParam(value = "author", required = false) String author){
        
        List<Book> bookList = new ArrayList<>(); //find the elements of the list by yourself
        if( author == null ){
            bookList = this.bookService.findBooksByGenre( genre, available );
        }
        else if( genre == null ){
            bookList = this.bookService.findBooksByAuthor( author, available);
        }
        else if( author == null && genre == null ){
            bookList = this.bookService.findByAvailability( available );
        }
        else{
            bookList = this.bookService.findBooksByGenreAuthor( genre, author, available );
        }

        return new ResponseEntity<>(bookList, HttpStatus.OK);

    }
}
