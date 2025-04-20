package com.hhplus.project.domain.notification;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
public class Notification {
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

    /** 생성일 */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** 수정일 */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
