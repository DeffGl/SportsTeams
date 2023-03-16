package com.example.SportsTeams.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@Getter
@Setter
public class TeamsErrorResponse {
    private String message;
    private Date date;
}
