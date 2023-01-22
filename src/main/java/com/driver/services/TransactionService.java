package com.driver.services;

import com.driver.models.Transaction;
import com.driver.models.TransactionStatus;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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

        Book book = this.bookRepository5.findById( bookId );
        Card card = this.cardRepository5.findById( cardId );
        if( book == null ){
            throw new Exception("Book is either unavailable or not present");
        }
        if( card == null ){
            throw new Exception("Card is invalid");
        }

        int noOfBooks = card.books.size();
        if( noOfBooks >= max_allowed_books ){
            throw new Exception("Book limit has reached for this card");
        }

        this.bookRepository5.update( book );

        Transaction transaction = new Transaction();
        transaction.transactionStatus = TransactionStatus.SUCCESSFUL;
        transaction.card = this.card;
        transaction.book = this.book;
        transaction.isIssueOperation = true;
        //Note that the error message should match exactly in all cases
        this.transactionRepository5.save( transaction );


       return transaction.transactionId; //return transactionId instead
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);

        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well

        Transaction transactionNew = new Transaction();
        transactionNew.transactionStatus = TransactionStatus.SUCCESSFUL;
        transactionNew.card = this.card;
        transactionNew.book = this.book;
        this.transactionRepository5.save( transaction );

        Date prevDate = this.transaction.transactionDate;
        Date currDate = this.transactionNew


        Transaction returnBookTransaction  = null;
        return returnBookTransaction; //return the transaction after updating all details
    }
}
