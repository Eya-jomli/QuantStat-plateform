package com.quantstat.matchmanagement.Repositories;

import com.quantstat.matchmanagement.Entities.MatchStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchStatisticRepository extends JpaRepository<MatchStatistic, Long> {
    List<MatchStatistic> findByTeamId(Long teamId);
}

