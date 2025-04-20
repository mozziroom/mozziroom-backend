package com.hhplus.project.interfaces.member;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
public class Member {
    /** 멤버 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    /** 이름 */
    @Column(name = "name", nullable = false)
    private String name;

    /** 프로필 사진 URL */
    @Column(name = "profile_img_url")
    private String profileImgUrl;

    /** 이메일 */
    @Column(name = "email", nullable = false)
    private String email;

    /** 생성일 */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** 수정일 */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
