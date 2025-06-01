package com.hhplus.project.infra.member.entity;

import com.hhplus.project.infra.BaseTimeEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "member")
public class MemberEntity extends BaseTimeEntity {
    /** 멤버 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    /** 이름 */
    @Column(name = "name", nullable = false)
    private String name;

    /** 닉네임 */
    @Column(name = "nickname", nullable = false)
    private String nickname;

    /** 프로필 사진 PATH */
    @Column(name = "profile_img_path")
    private String profileImgPath;

    /** 이메일 */
    @Column(name = "email", nullable = false)
    private String email;

    public MemberEntity() {

    }

    public MemberEntity(String name, String nickname, String profileImgPath, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.nickname = nickname;
        this.profileImgPath = profileImgPath;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberEntity member = (MemberEntity) o;
        return Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }

    public Long getMemberId() {
        return this.memberId;
    }
}
