package com.mauricio.onlinejob.dto;

import lombok.Getter;
import lombok.Setter;

public record UserDtoResponse(
        Long id,
        String name,
        String lastName,
        String birthday,
        String username,
        String createdAt,
        String updatedAt
) {
}
