package com.cs2300.cch_lib.service;

import com.cs2300.cch_lib.model.EquipmentListing;
import com.cs2300.cch_lib.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    private EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<EquipmentListing> findAllEquipment() {
        return equipmentRepository.findAllEquipment();
    }

    public List<EquipmentListing> searchEquipmentByType(String search) {
        return equipmentRepository.searchEquipmentByName(search);
    }
}
