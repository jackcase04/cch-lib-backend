package com.cs2300.cch_lib.service;

import com.cs2300.cch_lib.model.entity.*;
import com.cs2300.cch_lib.repository.BookRepository;
import com.cs2300.cch_lib.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private final BookRepository bookRepository;
    private final EquipmentRepository equipmentRepository;

    public UserService(
            BookRepository bookRepository,
            EquipmentRepository equipmentRepository
    ) {
        this.bookRepository = bookRepository;
        this.equipmentRepository = equipmentRepository;

    }

    //Gets items the user requested for checkout that are ready for the user to pick up.
    public CheckoutNotices getCheckOutNotices(Integer userId) {
        ArrayList<BookRequest> books = bookRepository.findCheckOutNotices(userId);
        ArrayList<EquipmentRequest> equipment = equipmentRepository.findCheckOutNotices(userId);
        return new CheckoutNotices(books, equipment);
    }














}
