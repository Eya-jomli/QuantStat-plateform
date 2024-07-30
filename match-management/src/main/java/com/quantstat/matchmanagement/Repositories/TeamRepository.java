package com.quantstat.matchmanagement.Repositories;

import com.quantstat.matchmanagement.Entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {}