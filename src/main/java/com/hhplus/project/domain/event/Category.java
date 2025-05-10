package com.hhplus.project.domain.event;

import com.hhplus.project.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "category")
@Getter
public class Category extends BaseTimeEntity {
    /** 카테고리 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    /** 카테고리명 */
    @Column(name = "name", nullable = false)
    private String name;

    /** 사용 여부 */
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    /** 상위 Depth id */
    @Column(name = "parent_id")
    private Long parentId;

    /** 정렬 순서 */
    @Column(name = "sort", nullable = false)
    private int sort;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId);
    }
}
