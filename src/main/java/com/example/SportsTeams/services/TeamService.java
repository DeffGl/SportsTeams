package com.example.SportsTeams.services;

import com.example.SportsTeams.models.Member;
import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.models.enums.Role;
import com.example.SportsTeams.models.enums.Type;
import com.example.SportsTeams.repositories.TeamRepository;
import com.example.SportsTeams.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return getTeams(teamRepository.findAll());
    }

    public List<Team> getAllTeamsByType(Type type) {
        return getTeams(teamRepository.findAllByType(type));
    }

    public List<Team> getAllTeamsCreatedBetween(Date firstDate, Date secondDate) {
        return getTeams(teamRepository.findByDateOfCreationBetween(firstDate, secondDate));
    }

    public List<Member> getAllTeamMember(int teamId) {
        Team team = getTeamById(teamId);
        return getMembers(team.getMembers());
    }

    public List<Member> getAllMembersTeamByRole(int teamId, Role role) {
        List<Member> members = getAllTeamMember(teamId);
        return getMembers(members
                .stream()
                .filter(member -> member.getRole().equals(role))
                .collect(Collectors.toList()));
    }

    @Transactional
    public void addTeam(Team team) {
        try {
            teamRepository.save(team.setDateOfCreation(new Date()));
        } catch (Exception e) {
            throw new TeamNotCreatedException();
        }
    }

    @Transactional
    public void editTeam(Team updatedTeam, int teamId) {
        checkTeamById(teamId);
        try {
            teamRepository.saveAndFlush(updatedTeam.setId(teamId).setDateOfCreation(new Date()));
        } catch (Exception e) {
            throw new TeamNotEditedException();
        }
    }

    @Transactional
    public void deleteTeam(int teamId) {
        checkTeamById(teamId);
        try {
            teamRepository.deleteById(teamId);
        }catch (Exception e){
            throw new TeamNotDeleteException();
        }
    }

    private List<Team> getTeams(List<Team> teams) {
        if (teams.isEmpty()) {
            throw new TeamsNotFoundException();

        }
        return teams;
    }

    private List<Member> getMembers(List<Member> members) {
        if (members.isEmpty()) {
            throw new MembersNotFoundException();
        }
        return members;
    }

    private Optional<Team> findTeam(int teamId) {
        return teamRepository.findById(teamId);
    }

    public void checkTeamById(int teamId) {
        if (!findTeam(teamId).isPresent()) {
            throw new TeamsNotFoundException();
        }
    }

    private Team getTeamById (int teamId){
        Optional<Team> team = findTeam(teamId);
        return team.orElseThrow(TeamsNotFoundException::new);
    }
}
