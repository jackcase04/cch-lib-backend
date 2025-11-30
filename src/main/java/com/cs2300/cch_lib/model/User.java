package com.cs2300.cch_lib.model;

public record User(
        Integer userId,
        String email,
        String password,
        String f_name,
        String m_init,
        String l_name,
        Boolean isAdmin
) {}