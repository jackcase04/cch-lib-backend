package com.cs2300.cch_lib.dto;

public record UserResponse(
        Integer userId,
        String email,
        String name,
        Boolean isAdmin
) {}
