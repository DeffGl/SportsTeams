package com.example.SportsTeams.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "team", schema = "public")
@Data
public class Team {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "date_of_creation")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfCreation;

    @OneToMany(mappedBy = "team")
    @ToString.Exclude
    @JsonIgnore
    private List<Member> members;

}
