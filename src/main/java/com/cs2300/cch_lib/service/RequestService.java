package com.cs2300.cch_lib.service;

import com.cs2300.cch_lib.exception.InvalidBookIdException;
import com.cs2300.cch_lib.exception.InvalidEquipmentIdException;
import com.cs2300.cch_lib.exception.InvalidResourceException;
import com.cs2300.cch_lib.exception.InvalidUserIdException;
import com.cs2300.cch_lib.model.entity.Request;
import com.cs2300.cch_lib.repository.BookRepository;
import com.cs2300.cch_lib.repository.EquipmentRepository;
import com.cs2300.cch_lib.repository.RequestRepository;
import com.cs2300.cch_lib.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final BookRepository bookRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    public RequestService(
            RequestRepository requestRepository,
            BookRepository bookRepository,
            EquipmentRepository equipmentRepository,
            UserRepository userRepository
    ) {
        this.requestRepository = requestRepository;
        this.bookRepository = bookRepository;
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
    }

    public Request addNewRequest(long userId, char resourceType, long resourceId, String requestType) {
        if (userRepository.getUserById(userId) == null) {
            throw new InvalidUserIdException("User with that id does not exist");
        }

        if (resourceType == 'B') {
            if (bookRepository.getBookById(resourceId) == null) {
                throw new InvalidBookIdException("Book with that id does not exist");
            }
        } else if (resourceType == 'E') {
            if (equipmentRepository.getEquipmentById(resourceId) == null) {
                throw new InvalidEquipmentIdException("Equipment with that id does not exist");
            }
        } else {
            throw new InvalidResourceException("Invalid resource type.");
        }

        return requestRepository.addNewRequest(userId, resourceType, resourceId, requestType);
    }
}
