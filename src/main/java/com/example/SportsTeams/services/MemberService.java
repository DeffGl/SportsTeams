package com.example.SportsTeams.services;

import com.example.SportsTeams.models.Member;
import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.repositories.MemberRepository;
import com.example.SportsTeams.repositories.TeamRepository;
import com.example.SportsTeams.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamService teamService;

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
        teamService.checkTeamById(newTeamId);
        try {
            memberRepository.save(member.setTeam(new Team().setId(newTeamId)));
        }catch (Exception e) {
            throw new MemberNotTransferredException();
        }
    }

    @Transactional
    public void editMember(Member updatedMember, int memberId){
        Member member = getMember(memberId);
        try {
            memberRepository.saveAndFlush(updatedMember.setId(memberId).setTeam(member.getTeam()));
        }catch (Exception e){
            throw new MemberNotEditedException();
        }
    }

    @Transactional
    public void deleteMember(int memberId){
        checkMemberById(memberId);
        try {
            memberRepository.deleteById(memberId);
        }catch (Exception e){
            throw new MemberNotDeleteException();
        }
    }

    private Member getMember(int memberId){
        Optional<Member> member = findMember(memberId);
        return member.orElseThrow(MembersNotFoundException::new);
    }

    private void checkMemberById(int memberId){
        if (!findMember(memberId).isPresent()) {
            throw new MembersNotFoundException();
        }
    }
    private Optional<Member> findMember(int memberId){
        return memberRepository.findById(memberId);
    }

}
