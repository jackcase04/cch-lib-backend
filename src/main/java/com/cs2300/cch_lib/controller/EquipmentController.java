package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.model.EquipmentListing;
import com.cs2300.cch_lib.service.EquipmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("/listings")
    public List<EquipmentListing> findAllEquipment() {
        return equipmentService.findAllEquipment();
    }

    @GetMapping("/search-equipment")
    public List<EquipmentListing> searchEquipment(
            @RequestParam String search
    ) {
        return equipmentService.searchEquipmentByType(search);
    }
}
