package com.driver.services;

import com.driver.models.Book;
import com.driver.models.Card;
import com.driver.models.CardStatus;
import com.driver.models.Transaction;
import com.driver.models.TransactionStatus;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository5;


    @Autowired
    CardRepository cardRepository5;


    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    public int max_allowed_books;

    @Value("${books.max_allowed_days}")
    public int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    public int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {
        //check whether bookId and cardId already exist
        //conditions required for successful transaction of issue book:
        //1. book is present and available
        // If it fails: throw new Exception("Book is either unavailable or not present");
        //2. card is present and activated
        // If it fails: throw new Exception("Card is invalid");
        //3. number of books issued against the card is strictly less than max_allowed_books
        // If it fails: throw new Exception("Book limit has reached for this card");
        //If the transaction is successful, save the transaction to the list of transactions and return the id

        Book book = this.bookRepository5.findById( bookId ).get();
        Card card = this.cardRepository5.findById( cardId ).get();

        Transaction transaction = new Transaction();
        
        transaction.setBook( book );
        transaction.setCard( card);
        transaction.setIssueOperation(true);
        
        
        //book should be available 
        if( book == null || !book.isAvailable() ){
        	transaction.setTransactionStatus(TransactionStatus.FAILED);
        	this.transactionRepository5.save( transaction );
            throw new Exception("Book is either unavailable or not present");
        }
        
        //card is unavailable or its deactivated
        if( card == null || card.getCardStatus().equals(CardStatus.DEACTIVATED) ){
            transaction.setTransactionStatus( TransactionStatus.FAILED );
            transactionRepository5.save(transaction);
        	throw new Exception("Card is invalid");
        }

        int noOfBooks = card.getBooks().size();
        if( noOfBooks >= max_allowed_books ){
        	transaction.setTransactionStatus( TransactionStatus.FAILED );
        	transactionRepository5.save(transaction);
            throw new Exception("Book limit has reached for this card");
        }
        
        book.setCard( card );
        book.setAvailable(false);
        List<Book> boolList = card.getBooks();
        boolList.add( book );
        card.setBooks(boolList);
        
        cardRepository5.save(card);
        bookRepository5.updateBook( book );
        
        transaction.setTransactionStatus( TransactionStatus.SUCCESSFUL );
        //Note that the error message should match exactly in all cases
        this.transactionRepository5.save( transaction );


       return transaction.getTransactionId(); //return transactionId instead
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);
        Date issueDate = transaction.getTransactionDate();
        long timeIssuetime = Math.abs( System.currentTimeMillis() - issueDate.getTime() );
        long noOfDaysPassed = TimeUnit.DAYS.convert( timeIssuetime, TimeUnit.MILLISECONDS);
        
        int fine = 0;
        if( noOfDaysPassed > getMax_allowed_days ) {
        	fine = (int)( (noOfDaysPassed - getMax_allowed_days) * fine_per_day );
        }
        Book book = transaction.getBook();
        book.setAvailable(true);
        book.setCard(null);
        
        bookRepository5.updateBook(book);
        
        Transaction tr = new Transaction();
        tr.setBook(transaction.getBook() );
        tr.setCard( transaction.getCard() );
        tr.setIssueOperation(false);
        tr.setFineAmount(fine);
        tr.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        
        transactionRepository5.save( tr );
        
        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well


        return tr; //return the transaction after updating all details
    }
}
