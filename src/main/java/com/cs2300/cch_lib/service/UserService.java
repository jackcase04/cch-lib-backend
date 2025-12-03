package com.cs2300.cch_lib.service;

import com.cs2300.cch_lib.model.entity.Book;
import com.cs2300.cch_lib.model.entity.Equipment;
import com.cs2300.cch_lib.dto.response.Response;
import com.cs2300.cch_lib.model.entity.LibraryItems;
import com.cs2300.cch_lib.repository.UserRepository;
import com.cs2300.cch_lib.repository.BookRepository;
import com.cs2300.cch_lib.repository.EquipmentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

@Service
public class GeneralService {
    private final BookRepository bookRepository;
    private final EquipmentRepository equipmentRepository;

    public GeneralService(
            BookRepository bookRepository,
            EquipmentRepository equipmentRepository
    ) {
        this.bookRepository = bookRepository;
        this.equipmentRepository = equipmentRepository;

    }


    public LibraryItems getCheckOutNotices(String userId) {
        LinkedList<Book> books = bookRepository.getCheckOutNotices(userId);
        LinkedList<Equipment> equipment = equipmentRepository.getCheckOutNotices(userId);
        return new LibraryItems(books, equipment);
    }














}
