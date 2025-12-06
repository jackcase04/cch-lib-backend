package com.cs2300.cch_lib.dto.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEquipmentRequest {
    public UpdateEquipmentRequest(Integer checkedOutBy) {
        this.equipmentName = null;
        this.classRequirement = null;
        this.checkedOut = null;
        this.additionalInfo = null;
        this.contact = null;
        this.checkedOutBy = checkedOutBy;
    }

    String equipmentName;
    String classRequirement;
    Boolean checkedOut;
    String additionalInfo;
    Integer contact;
    Integer checkedOutBy;
}
