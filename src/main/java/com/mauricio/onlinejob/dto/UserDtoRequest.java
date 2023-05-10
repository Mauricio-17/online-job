package com.mauricio.onlinejob.dto;

import lombok.Getter;
import lombok.Setter;

public record UserDtoRequest(
    String name,
    String lastName,
    String birthday,
    String username,
    String password,
    String createdAt,
    String updatedAt,
    Long userTypeId
) {}
