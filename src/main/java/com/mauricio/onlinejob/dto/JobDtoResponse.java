package com.mauricio.onlinejob.dto;

import java.util.List;

public record JobDtoResponse(
        List<JobDto> content,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
) {
}
