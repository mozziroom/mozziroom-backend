package com.hhplus.project.infra.member.entity;

import com.hhplus.project.domain.member.Member;
import com.hhplus.project.infra.BaseTimeEntity;
import com.hhplus.project.support.security.oauth2.ProviderType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class MemberEntity extends BaseTimeEntity {
    /**
     * 멤버 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 이름
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 닉네임
     */
    @Column(name = "nickname", nullable = false)
    private String nickname;

    /**
     * 프로필 사진 PATH
     */
    @Column(name = "profile_img_path")
    private String profileImgPath;

    /**
     * 이메일
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 로그인 플랫폼
     */
    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    /**
     * 로그인 플랫폼 고유 식별값
     */
    @Column(name = "providerId")
    private String providerId;

    @Builder
    private MemberEntity(Long memberId,
                         String name,
                         String nickname,
                         String profileImgPath,
                         String email,
                         ProviderType providerType,
                         String providerId
    ) {
        this.memberId = memberId;
        this.name = name;
        this.nickname = nickname;
        this.profileImgPath = profileImgPath;
        this.email = email;
        this.providerType = providerType;
        this.providerId = providerId;
    }

    public static MemberEntity create(
            String name,
            String nickname,
            String profileImgPath,
            String email,
            ProviderType providerType,
            String providerId
    ) {
        return new MemberEntity(
                null,
                name,
                nickname,
                profileImgPath,
                email,
                providerType,
                providerId
        );
    }

    @Builder
    private MemberEntity(Long memberId,
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

    public static MemberEntity create(
            String name,
            String nickname,
            String profileImgPath,
            String email
    ) {
        return new MemberEntity(
                null,
                name,
                nickname,
                profileImgPath,
                email
        );
    }

    public Member toDomain() {
        return new Member(
                this.memberId,
                this.name,
                this.nickname,
                this.profileImgPath,
                this.email,
                this.providerType,
                this.providerId
        );
    }

    public static MemberEntity fromDomain(Member member) {
        return new MemberEntity(
                member.memberId(),
                member.name(),
                member.nickname(),
                member.profileImageUrl(),
                member.email(),
                member.providerType(),
                member.providerId()
        );
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
