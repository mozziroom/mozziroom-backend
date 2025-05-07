package com.hhplus.project.domain.member;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

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

    /** 닉네임 */
    @Column(name = "닉네임", nullable = false)
    private String nickname;

    /** 프로필 사진 PATH */
    @Column(name = "profile_img_path")
    private String profileImgPath;

    /** 이메일 */
    @Column(name = "email", nullable = false)
    private String email;

    /** 생성일 */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** 수정일 */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}
