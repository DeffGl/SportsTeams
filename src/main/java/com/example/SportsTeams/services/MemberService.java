package com.example.SportsTeams.services;

import com.example.SportsTeams.models.Member;
import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void addMember(Member member, int id) {
        memberRepository.save(member.setTeam(new Team().setId(id)));
    }

    @Transactional
    public void transferMember(int memberId, int newTeamId){
        Optional<Member> member = getMemberById(memberId);
        if (member.isPresent()){
            Member updatedMember = member.get();
            updatedMember.setTeam(new Team().setId(newTeamId));
            memberRepository.save(updatedMember);
        }
    }

    @Transactional
    public void editMember(Member updatedMember, int memberId, int teamId){
        memberRepository.save(updatedMember.setId(memberId).setTeam(new Team().setId(teamId)));
    }

    @Transactional
    public void deleteMember(int memberId){
        memberRepository.deleteById(memberId);
    }

    private Optional<Member> getMemberById(int id){
        return Optional.ofNullable(memberRepository.findMemberById(id));
    }
}
