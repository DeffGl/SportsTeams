package com.example.SportsTeams.controllers;

import com.example.SportsTeams.dto.TeamDTO;
import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SportTeamController {

    private final TeamService teamService;
    private final ModelMapper modelMapper;

    @GetMapping("/teams")
    public List<TeamDTO> getAllTeams(){
        return teamService.getAllTeams().stream()
                .map(team->modelMapper.map(team, TeamDTO.class))
                .collect(Collectors.toList());
    }
}
