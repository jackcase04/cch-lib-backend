package com.cs2300.cch_lib.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorRequest {
    String fName;
    String mInit;
    String lName;
}
