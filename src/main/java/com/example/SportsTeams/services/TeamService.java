package com.example.SportsTeams.services;

import com.example.SportsTeams.models.Member;
import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public List<Team> getAllTeamsByType(String type){
        return teamRepository.findAllByType(type);
    }

    public List<Team> getAllTeamsCreatedBetween(Date firstDate, Date secondDate){
        return teamRepository.findByDateOfCreationBetween(firstDate, secondDate);
    }

    public List<Member> getAllTeamMember(int id){
        Optional<Team> team = teamRepository.findTeamById(id).stream().findAny();
        return team.map(Team::getMembers).orElse(new ArrayList<>());
    }

}
