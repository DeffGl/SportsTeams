package com.example.SportsTeams.services;

import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }
}
