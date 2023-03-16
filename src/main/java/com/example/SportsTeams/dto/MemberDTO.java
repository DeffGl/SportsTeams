package com.example.SportsTeams.dto;

import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.models.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;


import java.util.Date;

@Data
public class MemberDTO {
    @ToString.Exclude
    @JsonIgnore
    private Team team;

    private String surname;

    private String name;

    private String patronymic;

    private Date dateOfBirth;

    private Role role;
}
