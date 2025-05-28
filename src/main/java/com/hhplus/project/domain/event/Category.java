package com.hhplus.project.domain.event;

public record Category(
        Long categoryId,
        String name,
        boolean isActive,
        Long parentId,
        int sort
) {}
