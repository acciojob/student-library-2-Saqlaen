package com.driver.services;

import com.driver.models.Book;
import com.driver.models.Card;
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

        Optional<Book> book = this.bookRepository5.findById( bookId );
        Optional<Card> card = this.cardRepository5.findById( cardId );
        if( !book.isPresent() ){
            throw new Exception("Book is either unavailable or not present");
        }
        if( !card.isPresent() ){
            throw new Exception("Card is invalid");
        }

        int noOfBooks = card.get().getBooks().size();
        if( noOfBooks >= max_allowed_books ){
            throw new Exception("Book limit has reached for this card");
        }

        this.bookRepository5.updateBook( book.get() );

        Transaction transaction = new Transaction();
        transaction.setTransactionStatus( TransactionStatus.SUCCESSFUL );
        transaction.setCard( card.get() );
        transaction.setBook( book.get() );
        transaction.setIssueOperation(true);
        //Note that the error message should match exactly in all cases
        this.transactionRepository5.save( transaction );


       return transaction.getTransactionId(); //return transactionId instead
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);

        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well

        Optional<Book> book = this.bookRepository5.findById( bookId );
        Optional<Card> card = this.cardRepository5.findById( cardId );

        if( !book.isPresent() ){
            throw new Exception("Book is either unavailable or not present");
        }
        if( !card.isPresent() ){
            throw new Exception("Card is invalid");
        }

        Transaction returnBookTransaction = new Transaction();
        returnBookTransaction.setTransactionStatus( TransactionStatus.SUCCESSFUL );
        returnBookTransaction.setCard( card.get() );
        returnBookTransaction.setBook( book.get() );
        this.transactionRepository5.save( transaction );

        Date prevDate = transaction.getTransactionDate();
        Date currDate = returnBookTransaction.getTransactionDate();

        long prevdatetime = prevDate.getTime();
        long currdateTime = currDate.getTime();
        long timeDiff = Math.abs( currdateTime - prevdatetime );
        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS );
        long maxAllowed = (long)getMax_allowed_days;

        if( daysDiff < maxAllowed ){
            returnBookTransaction.setFineAmount( 0 );
        }
        else{
            long fineForNoOfDays = maxAllowed - daysDiff;
            returnBookTransaction.setFineAmount( fine_per_day * (int)fineForNoOfDays  );
        }

        return returnBookTransaction; //return the transaction after updating all details
    }
}
