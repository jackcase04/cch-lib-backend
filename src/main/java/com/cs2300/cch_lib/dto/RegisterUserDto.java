package com.cs2300.cch_lib.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String password;
    private String name;
    private Boolean isAdmin;
}
