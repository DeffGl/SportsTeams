package com.example.SportsTeams.controllers;

import com.example.SportsTeams.dto.MemberDTO;
import com.example.SportsTeams.dto.TeamDTO;
import com.example.SportsTeams.models.Member;
import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.models.enums.Role;
import com.example.SportsTeams.models.enums.Type;
import com.example.SportsTeams.services.MemberService;
import com.example.SportsTeams.services.TeamService;
import com.example.SportsTeams.util.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final MemberService memberService;

    @GetMapping()
    public List<TeamDTO> getAllTeams(){
        return teamService.getAllTeams().stream()
                .map(team->modelMapper.map(team, TeamDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/type")
    public List<TeamDTO> getAllTeamsByType(@RequestParam("type") Type type){
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
    public List<MemberDTO> getAllTeamMember(@RequestParam("id") int teamId){
        return teamService.getAllTeamMember(teamId).stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/members/role")
    public List<MemberDTO> getAllMembersTeamByRole(@RequestParam("id") int teamId, @RequestParam("role") Role role){
        return teamService.getAllMembersTeamByRole(teamId, role).stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addTeam(@RequestBody TeamDTO teamDTO){
        teamService.addTeam(modelMapper.map(teamDTO, Team.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/member/add")
    public ResponseEntity<HttpStatus> addMember(@RequestBody MemberDTO memberDTO, @RequestParam("id") int teamId){
        memberService.addMember(modelMapper.map(memberDTO, Member.class), teamId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/member/transfer")
    public void transferMember(@RequestParam("id1") int memberId, @RequestParam("id2") int newTeamId){
        memberService.transferMember(memberId, newTeamId);
    }

    @PatchMapping("/edit")
    public void editTeam(@RequestBody TeamDTO teamDTO, @RequestParam("id") int teamId){
        teamService.editTeam(modelMapper.map(teamDTO, Team.class), teamId);
    }

    @PatchMapping("/member/edit")
    public void editMember(@RequestBody MemberDTO memberDTO, @RequestParam("id1") int memberId, @RequestParam("id2") int teamId){
        memberService.editMember(modelMapper.map(memberDTO, Member.class), memberId, teamId);
    }

    @DeleteMapping("/delete")
    public void deleteTeam(@RequestParam("id") int teamId){
        teamService.deleteTeam(teamId);
    }

    @DeleteMapping("/member/delete")
    public void deleteMember(@RequestParam("id") int memberId){
        memberService.deleteMember(memberId);
    }

    @ExceptionHandler
    private ResponseEntity<TeamsErrorResponse> handleException (TeamsNotFoundException e){
        TeamsErrorResponse response = new TeamsErrorResponse("Teams is not found", new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MembersErrorResponse> handleException(MembersNotFoundException e){
        MembersErrorResponse response = new MembersErrorResponse("Members is not found", new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<TeamsErrorResponse> handleException(TeamNotCreatedException e){
        TeamsErrorResponse response = new TeamsErrorResponse("Create team is failed", new Date());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MembersErrorResponse> handleException(MemberNotCreatedException e){
        MembersErrorResponse response = new MembersErrorResponse("Create member is failed", new Date());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
