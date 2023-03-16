package com.example.SportsTeams.repositories;


import com.example.SportsTeams.models.Team;
import com.example.SportsTeams.models.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    List<Team> findAllByType(Type type);
    List<Team> findByDateOfCreationBetween(Date firstDate, Date secondDate);
    Team findTeamById(int id);
}
