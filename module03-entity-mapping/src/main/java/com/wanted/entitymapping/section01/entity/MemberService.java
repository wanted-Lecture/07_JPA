package com.wanted.entitymapping.section01.entity;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public void registMember(MemberRegistDTO newMember) {

        // 예외처리를 한다

        // dto -> entity로 변경해준다.

        Member member = new Member(
                newMember.getMemberId(),
                newMember.getMemberPwd(),
                newMember.getMemberName(),
                newMember.getPhone(),
                newMember.getAddress(),
                newMember.getEnrollDate(),
                newMember.getMemberRole(),
                newMember.getStatus()
        );

        memberRepository.save(member);
    }
}
