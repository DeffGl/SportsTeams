package com.example.SportsTeams.repositories;


import com.example.SportsTeams.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    List<Team> findAllByType(String type);
    List<Team> findByDateOfCreationBetween(Date firstDate, Date secondDate);
    List<Team> findTeamById(int id);
}
