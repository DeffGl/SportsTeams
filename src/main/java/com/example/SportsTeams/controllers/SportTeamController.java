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

    private final Response response;

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
    public ResponseEntity<HttpStatus> transferMember(@RequestParam("id1") int memberId, @RequestParam("id2") int newTeamId){
        memberService.transferMember(memberId, newTeamId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/edit")
    public ResponseEntity<HttpStatus> editTeam(@RequestBody TeamDTO teamDTO, @RequestParam("id") int teamId){
        teamService.editTeam(modelMapper.map(teamDTO, Team.class), teamId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/member/edit")
    public ResponseEntity<HttpStatus> editMember(@RequestBody MemberDTO memberDTO, @RequestParam("id") int memberId){
        memberService.editMember(modelMapper.map(memberDTO, Member.class), memberId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteTeam(@RequestParam("id") int teamId){
        teamService.deleteTeam(teamId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/member/delete")
    public ResponseEntity<HttpStatus> deleteMember(@RequestParam("id") int memberId){
        memberService.deleteMember(memberId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<TeamsErrorResponse> handleException (TeamsNotFoundException e){
        return response.createTeamResponseByNotFound("Teams is not found");
    }

    @ExceptionHandler
    private ResponseEntity<MembersErrorResponse> handleException(MembersNotFoundException e){
        return response.createMemberResponseByNotFound("Members is not found");
    }

    @ExceptionHandler
    private ResponseEntity<TeamsErrorResponse> handleException(TeamNotCreatedException e){
        return response.createTeamResponseByBadRequest("Create team is failed");
    }

    @ExceptionHandler
    private ResponseEntity<MembersErrorResponse> handleException(MemberNotCreatedException e){
        return response.createMemberResponseByBadRequest("Create member is failed");
    }

    @ExceptionHandler
    private ResponseEntity<MembersErrorResponse> handleException (MemberNotTransferredException e){
        return response.createMemberResponseByBadRequest("Transfer member is failed");
    }

    @ExceptionHandler ResponseEntity<TeamsErrorResponse> handleException (TeamNotEditedException e){
        return response.createTeamResponseByBadRequest("Edit team is failed");
    }

    @ExceptionHandler
    private ResponseEntity<MembersErrorResponse> handleException(MemberNotEditedException e){
        return response.createMemberResponseByBadRequest("Edit member is failed");
    }

    @ExceptionHandler
    private ResponseEntity<TeamsErrorResponse> handleException(TeamNotDeleteException e){
        return response.createTeamResponseByBadRequest("Delete team is failed");
    }

    @ExceptionHandler
    private ResponseEntity<MembersErrorResponse> handleException(MemberNotDeleteException e){
        return response.createMemberResponseByBadRequest("Delete member is failed");
    }


}
