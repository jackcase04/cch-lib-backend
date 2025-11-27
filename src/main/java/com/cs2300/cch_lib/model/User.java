package com.cs2300.cch_lib.model;

public record User(
        Integer userId,
        String email,
        String password,
        String name,
        Boolean isAdmin
) {}