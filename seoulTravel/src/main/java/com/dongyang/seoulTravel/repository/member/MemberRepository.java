package com.dongyang.seoulTravel.repository.member;

import com.dongyang.seoulTravel.entity.member.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}

