package com.quantstat.matchmanagement.Repositories;

import com.quantstat.matchmanagement.Entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT m FROM Match m JOIN FETCH m.homeTeam JOIN FETCH m.awayTeam")
    List<Match> findAllMatchesWithTeams();
}