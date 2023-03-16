package com.example.SportsTeams.services;

import com.example.SportsTeams.models.Member;
import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.models.enums.Role;
import com.example.SportsTeams.models.enums.Type;
import com.example.SportsTeams.repositories.TeamRepository;
import com.example.SportsTeams.util.MembersNotFoundException;
import com.example.SportsTeams.util.TeamsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    public List<Team> getAllTeams(){
        return checkTeams(teamRepository.findAll());
    }

    public List<Team> getAllTeamsByType(Type type){
        return checkTeams(teamRepository.findAllByType(type));
    }

    public List<Team> getAllTeamsCreatedBetween(Date firstDate, Date secondDate){
        return checkTeams(teamRepository.findByDateOfCreationBetween(firstDate, secondDate));
    }

    public List<Member> getAllTeamMember(int teamId){
        Optional<Team> team = getTeamById(teamId);
        return team.map(Team::getMembers).orElseThrow(MembersNotFoundException::new);
    }

    public List<Member> getAllMembersTeamByRole (int teamId, Role role){
        List<Member> members = getAllTeamMember(teamId);
        return checkMembers(members
                .stream()
                .filter(member -> member.getRole().equals(role))
                .collect(Collectors.toList()));
    }

    @Transactional
    public void addTeam(Team team){
        teamRepository.save(team.setDateOfCreation(new Date()));
    }

    @Transactional
    public void editTeam(Team updatedTeam, int teamId){
        teamRepository.save(updatedTeam.setId(teamId).setDateOfCreation(new Date()));
    }

    @Transactional
    public void deleteTeam(int teamId){
        teamRepository.deleteById(teamId);
    }

    private Optional<Team> getTeamById(int id){
        return Optional.ofNullable(teamRepository.findTeamById(id));
    }

    private List<Team> checkTeams(List<Team> teams){
        if (teams.isEmpty()){
            throw new TeamsNotFoundException();
        }
        return teams;
    }

    private List<Member> checkMembers(List<Member> members){
        if (members.isEmpty()){
            throw new MembersNotFoundException();
        }
        return members;
    }
}
