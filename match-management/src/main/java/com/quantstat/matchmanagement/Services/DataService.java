package com.quantstat.matchmanagement.Services;

import com.quantstat.matchmanagement.Entities.Match;
import com.quantstat.matchmanagement.Entities.MatchStatistic;
import com.quantstat.matchmanagement.Entities.Team;
import com.quantstat.matchmanagement.Repositories.MatchRepository;
import com.quantstat.matchmanagement.Repositories.MatchStatisticRepository;
import com.quantstat.matchmanagement.Repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchStatisticRepository statisticRepository;

    public void saveTeams(List<Team> teams) {
        teamRepository.saveAll(teams);
    }

    public void saveMatches(List<Match> matches) {
        matchRepository.saveAll(matches);
    }

    public void saveStatistics(List<MatchStatistic> statistics) {
        statisticRepository.saveAll(statistics);
    }
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }
}
