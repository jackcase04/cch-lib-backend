package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.exception.UnauthorizedException;
import com.cs2300.cch_lib.model.projection.EquipmentListing;
import com.cs2300.cch_lib.service.AuthenticationService;
import com.cs2300.cch_lib.service.EquipmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    private final EquipmentService equipmentService;
    private final AuthenticationService authenticationService;

    public EquipmentController(EquipmentService equipmentService, AuthenticationService authenticationService) {
        this.equipmentService = equipmentService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/listings")
    public List<EquipmentListing> findAllEquipment(
            HttpSession session
    ) {
        if (!authenticationService.isLoggedIn(session)) {
            throw new UnauthorizedException("Please log in");
        }

        return equipmentService.findAllEquipment();
    }

    @GetMapping("/search")
    public List<EquipmentListing> searchEquipment(
            @RequestParam String search,
            HttpSession session
    ) {
        if (!authenticationService.isLoggedIn(session)) {
            throw new UnauthorizedException("Please log in");
        }

        return equipmentService.searchEquipmentByType(search);
    }
}
