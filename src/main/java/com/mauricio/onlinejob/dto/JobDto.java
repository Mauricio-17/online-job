package com.mauricio.onlinejob.dto;

public record JobDto(
        Long id,
        String title,
        Float salary,
        String jobType,
        String location,
        Long companyId,
        String createdAt,
        String updatedAt
) {
}
