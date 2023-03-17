package com.example.SportsTeams.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class Response {

    public ResponseEntity<TeamsErrorResponse> createTeamResponseByNotFound(String message){
        return new ResponseEntity<>(createTeamsErrorResponse(message), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<MembersErrorResponse> createMemberResponseByNotFound(String message){
        return new ResponseEntity<>(createMembersErrorResponse(message), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<TeamsErrorResponse> createTeamResponseByBadRequest(String message){
        return new ResponseEntity<>(createTeamsErrorResponse(message), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<MembersErrorResponse> createMemberResponseByBadRequest(String message){
        return new ResponseEntity<>(createMembersErrorResponse(message), HttpStatus.BAD_REQUEST);
    }

    public MembersErrorResponse createMembersErrorResponse(String message){
        return new MembersErrorResponse(message, new Date());
    }

    public TeamsErrorResponse createTeamsErrorResponse(String message){
        return new TeamsErrorResponse(message, new Date());
    }
}
