package com.cs2300.cch_lib.dto;

public record UserResponse(
        Integer userId,
        String email,
        String f_name,
        String m_init,
        String l_name,
        Boolean isAdmin
) {}
