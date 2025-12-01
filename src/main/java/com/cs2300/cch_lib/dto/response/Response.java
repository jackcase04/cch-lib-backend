package com.cs2300.cch_lib.dto.response;

public record Response<T>(boolean success, String msg, T data) {}