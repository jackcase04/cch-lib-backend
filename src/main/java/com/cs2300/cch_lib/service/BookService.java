package com.cs2300.cch_lib.service;

import com.cs2300.cch_lib.dto.request.UpdateBookRequest;
import com.cs2300.cch_lib.dto.response.UpdateBookResponse;
import com.cs2300.cch_lib.exception.InvalidBookIdException;
import com.cs2300.cch_lib.model.entity.Book;
import com.cs2300.cch_lib.model.projection.BookListing;
import com.cs2300.cch_lib.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookListing> findAllListings() {
        return bookRepository.findAllListings();
    }

    public List<BookListing> searchBooksByTitle(String search) {
        return bookRepository.searchBooksByTitle(search);
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
