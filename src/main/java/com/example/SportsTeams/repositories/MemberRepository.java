package com.example.SportsTeams.repositories;

import com.example.SportsTeams.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findMemberById(int id);
}
