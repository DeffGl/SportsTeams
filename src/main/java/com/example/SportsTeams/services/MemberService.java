package com.example.SportsTeams.services;

import com.example.SportsTeams.models.Member;
import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.repositories.MemberRepository;
import com.example.SportsTeams.util.MemberNotCreatedException;
import com.example.SportsTeams.util.MemberNotEditedException;
import com.example.SportsTeams.util.MemberNotTransferredException;
import com.example.SportsTeams.util.MembersNotFoundException;
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
    public void addMember(Member member, int teamId) {
        try {
            memberRepository.save(member.setTeam(new Team().setId(teamId)));
        }catch (Exception e){
            throw new MemberNotCreatedException();
        }
    }

    @Transactional
    public void transferMember(int memberId, int newTeamId){
        Member member = getMember(memberId);
        try {
            memberRepository.save(member.setTeam(new Team().setId(newTeamId)));
        }catch (Exception e) {
            throw new MemberNotTransferredException();
        }
    }

    @Transactional
    public void editMember(Member updatedMember, int memberId, int teamId){

        try {
            memberRepository.save(updatedMember.setId(memberId).setTeam(new Team().setId(teamId)));
        }catch (Exception e){
            throw new MemberNotEditedException();
        }
    }

    @Transactional
    public void deleteMember(int memberId){
        memberRepository.deleteById(memberId);
    }

    private Member getMember(int memberId){
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()){
            return member.get();
        }
        throw new MembersNotFoundException();
    }
    private void checkMember
}
