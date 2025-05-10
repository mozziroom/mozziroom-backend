package com.hhplus.project.infra.notification.entity;

import com.hhplus.project.infra.BaseTimeEntity;
import com.hhplus.project.domain.notification.NotificationEnums;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "notification")
public class NotificationEntity extends BaseTimeEntity {
    /** 알림 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    /** 수신자 id */
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    /** 알림 제목 */
    @Column(name = "title", nullable = false)
    private String title;

    /** 알림 내용 */
    @Column(name = "content", nullable = false)
    private String content;

    /** 알림 구분 (예약 승인, 예약 취소 등) */
    @Column(name = "type", nullable = false)
    private NotificationEnums.Type type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationEntity that = (NotificationEntity) o;
        return Objects.equals(notificationId, that.notificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId);
    }
}
