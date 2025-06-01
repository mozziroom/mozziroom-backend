package com.hhplus.project.infra.member.entity;

import com.hhplus.project.domain.member.Member;
import com.hhplus.project.infra.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Column(name = "닉네임", nullable = false)
    private String nickname;

    /** 프로필 사진 PATH */
    @Column(name = "profile_img_path")
    private String profileImgPath;

    /** 이메일 */
    @Column(name = "email", nullable = false)
    private String email;

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

    @Builder
    public MemberEntity(Long memberId,
                        String name,
                        String nickname,
                        String profileImgPath,
                        String email) {
        this.memberId = memberId;
        this.name = name;
        this.nickname = nickname;
        this.profileImgPath = profileImgPath;
        this.email = email;
    }

    public Member toDomain() {
        return new Member(
                this.memberId,
                this.name,
                this.nickname,
                this.profileImgPath,
                this.email
        );
    }

    public static MemberEntity fromDomain(Member member) {
        return new MemberEntity(
                member.memberId(),
                member.name(),
                member.nickname(),
                member.profileImgPath(),
                member.email()
        );
    }

    public long getMemberId() {
        return this.memberId;
    }
}
