package com.mauricio.onlinejob.dto;

public record UserDto(
    Long id,
    String name,
    String lastName,
    String birthday,
    String username,
    String createdAt,
    String updatedAt,
    Long userTypeId
) {}
