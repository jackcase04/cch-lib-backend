package com.cs2300.cch_lib.service;

import com.cs2300.cch_lib.model.entity.Book;
import com.cs2300.cch_lib.model.entity.Equipment;
import com.cs2300.cch_lib.model.entity.LibraryItems;
import com.cs2300.cch_lib.repository.BookRepository;
import com.cs2300.cch_lib.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final EquipmentRepository equipmentRepository;

    public LibraryService(
            BookRepository bookRepository,
            EquipmentRepository equipmentRepository
    ) {
        this.bookRepository = bookRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public LibraryItems searchLibrary(String bookTitle, String bookAuthor, String equipmentName) {

        List<Book> books = bookRepository.searchBooks(bookTitle, bookAuthor);

        List<Equipment> equipment = equipmentRepository.searchEquipmentByName(equipmentName);

        return new LibraryItems(books, equipment);
    }
}
