package com.cs2300.cch_lib.service;

import com.cs2300.cch_lib.dto.request.AddEquipmentRequest;
import com.cs2300.cch_lib.dto.request.UpdateEquipmentRequest;
import com.cs2300.cch_lib.dto.response.UpdateEquipmentResponse;
import com.cs2300.cch_lib.exception.InvalidEquipmentIdException;
import com.cs2300.cch_lib.model.entity.Equipment;
import com.cs2300.cch_lib.model.projection.EquipmentListing;
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

    public List<Equipment> searchEquipmentByType(String search) {
        return equipmentRepository.searchEquipmentByName(search);
    }

    public Equipment addEquipment(AddEquipmentRequest request) {
        return equipmentRepository.addNewEquipment(request);
    }

    public UpdateEquipmentResponse updateEquipment(UpdateEquipmentRequest request, long equipmentId) {
        Equipment equipment = equipmentRepository.getEquipmentById(equipmentId);

        if (equipment == null)  {
            throw new InvalidEquipmentIdException("Equipment with that id does not exist");
        }

        equipment = equipmentRepository.updateEquipment(request, equipmentId);

        return new UpdateEquipmentResponse(
                equipment.equipmentName(),
                equipment.classRequirement(),
                equipment.checkedOut(),
                equipment.additionalInfo(),
                equipment.contactId(),
                equipment.checkedOutBy()
        );
    }

    public void deleteEquipment(long id) {
        if (equipmentRepository.getEquipmentById(id) == null) {
            throw new InvalidEquipmentIdException("Equipment with that id does not exist");
        }

        equipmentRepository.deleteEquipment(id);
    }
}
