package com.driver.services;

import com.driver.models.Book;
import com.driver.models.Card;
import com.driver.models.CardStatus;
import com.driver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository2;

    public void createBook(Book book){
        this.bookRepository2.save(book);
    }

    public List<Book> getBooks(String genre, boolean available, String author){
        List<Book> books = findBooksByGenreAuthor(genre, author, available);
        
        if( author == null ) {
        	books = findBooksByGenre(genre, available);
        }
        else if( genre == null ) {
        	books = findBooksByAuthor(author, available);
        }
        else if( available == false ) {
        	books = findBooksByAvailability(available);
        }
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
    
    public List<Book> findBooksByAvailability( boolean available ){
    	List<Book> books = this.bookRepository2.findAll();
    	List<Book> filtered = new ArrayList<>();
    	for( Book book  : books ) {
    		if( book.getAvailable() == available ) {
    			filtered.add( book );
    		}
    	}
    	return filtered;
    }
    
    public List<Book> findBooksByCardAvailable(){
    	List<Book> books = this.bookRepository2.findAll();
    	List<Book> filtered = new ArrayList<>();
    	for( Book book  : books ) {
    		Card card = book.getCard();
    		if( card.getCardStatus() == CardStatus.DEACTIVATED ) {
    			filtered.add(book);
    		}
    	}
    	return filtered;
    }
    
    public List<Book> findAllBooks(){
    	return this.bookRepository2.findAll();
    }
}
