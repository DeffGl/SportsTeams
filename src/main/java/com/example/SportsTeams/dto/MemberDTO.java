package com.example.SportsTeams.dto;

import com.example.SportsTeams.models.Team;
import lombok.Data;


import java.util.Date;

@Data
public class MemberDTO {
    private Team team;

    private String surname;

    private String name;

    private String patronymic;

    private Date dateOfBirth;

    private String role;
}
