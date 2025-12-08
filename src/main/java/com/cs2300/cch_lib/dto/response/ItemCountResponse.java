package com.cs2300.cch_lib.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemCountResponse {
    Integer bookCount;
    Integer equipmentCount;
}
