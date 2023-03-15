package com.example.SportsTeams.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "member", schema = "public")
@Data
public class Member {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private Team team;

    @Column(name="surname")
    private String surname;

    @Column(name="name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Column(name = "role")
    private String role;

}
