package com.hhplus.project.infra.member;

import com.hhplus.project.domain.member.Member;
import com.hhplus.project.domain.member.MemberRepository;
import com.hhplus.project.infra.member.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public MemberEntity save(MemberEntity memberEntity) {
        return memberJpaRepository.save(memberEntity);
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(MemberEntity.fromDomain(member)).toDomain();
    }
}
