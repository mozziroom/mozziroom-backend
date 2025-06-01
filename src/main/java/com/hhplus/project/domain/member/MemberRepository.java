package com.hhplus.project.domain.member;

import com.hhplus.project.infra.member.entity.MemberEntity;

public interface MemberRepository {

    MemberEntity save(MemberEntity memberEntity);

    Member save(Member member);
}
