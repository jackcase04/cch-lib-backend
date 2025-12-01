package com.cs2300.cch_lib.service;

import com.cs2300.cch_lib.dto.request.AddBookRequest;
import com.cs2300.cch_lib.dto.request.AuthorRequest;
import com.cs2300.cch_lib.dto.request.UpdateBookRequest;
import com.cs2300.cch_lib.dto.response.UpdateBookResponse;
import com.cs2300.cch_lib.exception.InvalidBookIdException;
import com.cs2300.cch_lib.model.entity.Author;
import com.cs2300.cch_lib.model.entity.Book;
import com.cs2300.cch_lib.model.projection.BookListing;
import com.cs2300.cch_lib.repository.AuthorRepository;
import com.cs2300.cch_lib.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookListing> findAllListings() {
        return bookRepository.findAllListings();
    }

    public List<BookListing> searchBooksByTitle(String search) {
        return bookRepository.searchBooksByTitle(search);
    }

    public List<BookListing> searchBooksByAuthor(String search) {
        return bookRepository.searchBooksByAuthor(search);
    }

    public Book addNewBook(AddBookRequest request) {

        Book book = bookRepository.addNewBook(request);
        long book_id = book.bookId();

        for (AuthorRequest author : request.getAuthors()) {
            // If the author doesn't exist, we need to create it.

            Author temp = authorRepository.getAuthorByAllInfo(author);

            if (temp == null) {
                temp = authorRepository.addNewAuthor(author);
            }

            long author_id = temp.authorId();

            authorRepository.addNewWrite(author_id, book_id);
        }

        return book;
    }

    public UpdateBookResponse updateBook(UpdateBookRequest request) {
        Book book = bookRepository.getBookById(request.getBookId());

        if (book == null)  {
            throw new InvalidBookIdException("Book with that id does not exist");
        }

        book = bookRepository.updateBook(request);

        return new UpdateBookResponse(
                book.bookId(),
                book.title(),
                book.course(),
                book.bookEdition(),
                book.condition(),
                book.isbn(),
                book.additionalInfo()
        );
    }
}
