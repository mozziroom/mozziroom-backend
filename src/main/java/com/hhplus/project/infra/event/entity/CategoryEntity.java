package com.hhplus.project.infra.event.entity;

import com.hhplus.project.domain.event.Category;
import com.hhplus.project.infra.BaseTimeEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "category")
public class CategoryEntity extends BaseTimeEntity {
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
        CategoryEntity category = (CategoryEntity) o;
        return Objects.equals(categoryId, category.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId);
    }


    public Category toDomain(){
        return new Category(
                categoryId,
                name,
                isActive,
                parentId,
                sort
        );
    }

    public static CategoryEntity fromDomain(Category category){
        CategoryEntity entity = new CategoryEntity();
        entity.categoryId = category.categoryId();
        entity.name       = category.name();
        entity.isActive   = category.isActive();
        entity.sort       = category.sort();
        entity.parentId   = category.parentId();
        return entity;
    }
}
