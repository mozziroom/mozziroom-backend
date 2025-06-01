package com.hhplus.project.domain.event;

public record Category(
        Long categoryId,
        String name,
        boolean isActive,
        Long parentId,
        int sort
) {

    public static Category create(String name, boolean isActive, Long parentId, int sort) {
        return new Category(null, name, isActive, parentId, sort);
    }
}
