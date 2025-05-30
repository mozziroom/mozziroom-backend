package com.hhplus.project.infra.member.entity;

import com.hhplus.project.domain.member.Member;
import com.hhplus.project.domain.member.oauth2.ProviderType;
import com.hhplus.project.infra.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@Getter
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

    /** 로그인 플랫폼 */
    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    /** 로그인 플랫폼 고유 식별값 */
    @Column(name = "providerId")
    private String providerId;

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

    public long getMemberId() {
        return this.memberId;
    }

    public Member.Info toDomain() {
        return Member.Info.create(
                this.memberId,
                this.name,
                this.nickname,
                this.profileImgPath,
                this.email,
                this.providerType
        );
    }
}
