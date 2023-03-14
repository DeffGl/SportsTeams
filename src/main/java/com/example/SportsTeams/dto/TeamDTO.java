package com.example.SportsTeams.dto;



import com.example.SportsTeams.models.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
public class TeamDTO {

    private String name;

    private String type;

    private Date dateOfCreation;

    @ToString.Exclude
    @JsonIgnore
    private List<Member> members;
}
