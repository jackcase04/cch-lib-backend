package com.cs2300.cch_lib.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SessionInfo {
    Boolean loggedIn;
    Integer userId;
    String email;
    Boolean isAdmin;
}
