package com.cs2300.cch_lib.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddEquipmentRequest {
    String equipmentName;
    String classRequirement;
    String additionalInfo;
    Integer contact;
}
