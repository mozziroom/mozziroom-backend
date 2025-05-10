package com.hhplus.project.infra.event.entity;

import com.hhplus.project.infra.BaseTimeEntity;
import com.hhplus.project.domain.event.EventEnums;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "event_image")
public class EventImageEntity extends BaseTimeEntity {
    /** 이벤트 이미지 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_image_id")
    private Long eventImageId;

    /** 이벤트 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private EventEntity event;

    /** 이미지 타입 */
    @Column(name = "image_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventEnums.ImageType imageType;

    /** 원본 파일 PATH */
    @Column(name = "origin_image_path", nullable = false)
    private String originImagePath;

    /** 파일 PATH */
    @Column(name = "image_path", nullable = false)
    private String imagePath;

    /** 정렬 순서 */
    @Column(name = "sort", nullable = false)
    private int sort;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventImageEntity that = (EventImageEntity) o;
        return Objects.equals(eventImageId, that.eventImageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventImageId);
    }
}
