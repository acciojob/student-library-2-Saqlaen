package com.driver.services;

import com.driver.models.Book;
import com.driver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository2;

    public void createBook(Book book){
        bookRepository2.save(book);
    }

    public List<Book> getBooks(String genre, boolean available, String author){
        List<Book> books = null; //find the elements of the list by yourself
        return books;
    }

    public List<Book> findBooksByGenre( String genre, boolean available ){
        return this.bookRepository2.findBooksByGenre( genre, available );
    }

    public List<Book> findBooksByAuthor( String auhorname, boolean available ){
        return this.bookRepository2.findBooksByAuthor( auhorname, available );
    }

    public List<Book> findByAvailability( boolean available ){
        return this.bookRepository2.findByAvailability(  available );
    }

    public List<Book> findBooksByGenreAuthor( String genre, String author, boolean available ){
        return this.bookRepository2.findBooksByGenreAuthor( genre, author, available );
    }
}
