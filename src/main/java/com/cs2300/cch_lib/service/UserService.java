package com.cs2300.cch_lib.service;

import com.cs2300.cch_lib.dto.request.UpdateBookRequest;
import com.cs2300.cch_lib.dto.request.UpdateEquipmentRequest;
import com.cs2300.cch_lib.exception.InvalidUserIdException;
import com.cs2300.cch_lib.model.entity.*;
import com.cs2300.cch_lib.repository.BookRepository;
import com.cs2300.cch_lib.repository.EquipmentRepository;
import com.cs2300.cch_lib.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private final BookRepository bookRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    public UserService(
            BookRepository bookRepository,
            EquipmentRepository equipmentRepository,
            UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
    }

    //Gets items the user requested for checkout that are ready for the user to pick up.
    public CheckoutNotices getCheckOutNotices(Integer userId) {
        ArrayList<BookRequest> books = bookRepository.findCheckOutNotices(userId);
        ArrayList<EquipmentRequest> equipment = equipmentRepository.findCheckOutNotices(userId);
        return new CheckoutNotices(books, equipment);
    }

    //Gets items the user is currently in possession of.
    public LibraryItems getUserItems(Integer userId) {
        ArrayList<Book> books = bookRepository.findUserBooks(userId);
        ArrayList<Equipment> equipment = equipmentRepository.findUserEquipment(userId);
        return new LibraryItems(books, equipment);
    }

    public void deleteUser(int userId) {
        User user = userRepository.getUserById(userId);

        if (user == null) {
            throw new InvalidUserIdException("User with that id does not exist");
        }

        // We'll have to set null all their relations to items checked out
        LibraryItems items = getUserItems(userId);

        for (Equipment equipment : items.equipment()) {
            UpdateEquipmentRequest request = new UpdateEquipmentRequest(-1);

            equipmentRepository.updateEquipment(request, equipment.equipmentId());
        }

        for (Book book : items.books()) {
            UpdateBookRequest request = new UpdateBookRequest(-1);

            bookRepository.updateBook(request, book.bookId());
        }

        userRepository.deleteUser(userId);
    }
}
