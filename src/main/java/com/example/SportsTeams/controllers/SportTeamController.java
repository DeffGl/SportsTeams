package com.example.SportsTeams.controllers;

import com.example.SportsTeams.dto.MemberDTO;
import com.example.SportsTeams.dto.TeamDTO;
import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class SportTeamController {

    private final TeamService teamService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<TeamDTO> getAllTeams(){
        return teamService.getAllTeams().stream()
                .map(team->modelMapper.map(team, TeamDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/type")
    public List<TeamDTO> getAllTeamsByType(@RequestParam("type") String type){
        return teamService.getAllTeamsByType(type).stream()
                .map(team->modelMapper.map(team, TeamDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/created")
    public List<TeamDTO> getAllTeamsCreatedBetween(@RequestParam("d1") Date firstDate, @RequestParam("d2") Date secondDate){
        return teamService.getAllTeamsCreatedBetween(firstDate, secondDate).stream()
                .map(team->modelMapper.map(team, TeamDTO.class))
                .collect(Collectors.toList());

    }

    @GetMapping("/members")
    public List<MemberDTO> getAllTeamMember(@RequestParam("id") int id){
        return teamService.getAllTeamMember(id).stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/members/role")
    public List<MemberDTO> getAllMembersTeamByRole(@RequestParam("id") int id, @RequestParam("role") String role){
        return teamService.getAllMembersTeamByRole(id, role).stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public void createTeam(@RequestBody TeamDTO teamDTO){
        teamService.createTeam(modelMapper.map(teamDTO, Team.class));
    }
}
