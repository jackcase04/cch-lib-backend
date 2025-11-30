package com.cs2300.cch_lib.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String password;
    private String fName;
    private String mInit;
    private String lName;
    private Boolean isAdmin;
}
