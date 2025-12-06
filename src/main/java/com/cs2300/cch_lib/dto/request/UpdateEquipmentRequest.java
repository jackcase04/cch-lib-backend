package com.cs2300.cch_lib.dto.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEquipmentRequest {
    String equipmentName;
    String classRequirement;
    Boolean checkedOut;
    String additionalInfo;
    Integer contact;
    Integer checkedOutBy;
}
