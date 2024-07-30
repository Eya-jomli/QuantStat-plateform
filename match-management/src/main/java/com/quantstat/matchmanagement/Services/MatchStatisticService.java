package com.quantstat.matchmanagement.Services;

import com.quantstat.matchmanagement.DTO.TeamStatisticsDTO;
import com.quantstat.matchmanagement.Entities.MatchStatistic;
import com.quantstat.matchmanagement.Repositories.MatchStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchStatisticService {
    private final MatchStatisticRepository matchStatisticRepository;

    @Autowired
    public MatchStatisticService(MatchStatisticRepository matchStatisticRepository) {
        this.matchStatisticRepository = matchStatisticRepository;
    }

    public List<MatchStatistic> getAllMatchStatistics() {
        return matchStatisticRepository.findAll();
    }

    public MatchStatistic getMatchStatisticById(Long id) {
        return matchStatisticRepository.findById(id).get();
    }

    public MatchStatistic saveMatchStatistic(MatchStatistic matchStatistic) {
        return matchStatisticRepository.save(matchStatistic);
    }

    public void deleteMatchStatistic(Long id) {
        matchStatisticRepository.deleteById(id);
    }


    public List<MatchStatistic> getStatisticsByTeamId(Long teamId) {
        return matchStatisticRepository.findByTeamId(teamId);
    }

    public TeamStatisticsDTO getAggregatedStatisticsByTeamId(Long teamId) {
        List<MatchStatistic> statistics = matchStatisticRepository.findByTeamId(teamId);
        TeamStatisticsDTO aggregatedStatistics = new TeamStatisticsDTO();

        for (MatchStatistic stat : statistics) {
            aggregatedStatistics.setGoals(aggregatedStatistics.getGoals() + (stat.getGoals() != null ? stat.getGoals() : 0));
            aggregatedStatistics.setGoalsConceded(aggregatedStatistics.getGoalsConceded() + (stat.getGoalsConceded() != null ? stat.getGoalsConceded() : 0));
            aggregatedStatistics.setFouls(aggregatedStatistics.getFouls() + (stat.getFouls() != null ? stat.getFouls() : 0));
            aggregatedStatistics.setYellowCards(aggregatedStatistics.getYellowCards() + (stat.getYellowCards() != null ? stat.getYellowCards() : 0));
            aggregatedStatistics.setRedCards(aggregatedStatistics.getRedCards() + (stat.getRedCards() != null ? stat.getRedCards() : 0));
            aggregatedStatistics.setCorners(aggregatedStatistics.getCorners() + (stat.getCorners() != null ? stat.getCorners() : 0));
            aggregatedStatistics.setShots(aggregatedStatistics.getShots() + (stat.getShots() != null ? stat.getShots() : 0));
            aggregatedStatistics.setShotsOnTarget(aggregatedStatistics.getShotsOnTarget() + (stat.getShotsOnTarget() != null ? stat.getShotsOnTarget() : 0));
            aggregatedStatistics.setEntrancesToOpponentsHalf(aggregatedStatistics.getEntrancesToOpponentsHalf() + (stat.getEntrancesToOpponentsHalf() != null ? stat.getEntrancesToOpponentsHalf() : 0));
            aggregatedStatistics.setEntrancesToOpponentsBox(aggregatedStatistics.getEntrancesToOpponentsBox() + (stat.getEntrancesToOpponentsBox() != null ? stat.getEntrancesToOpponentsBox() : 0));
            aggregatedStatistics.setLostBalls(aggregatedStatistics.getLostBalls() + (stat.getLostBalls() != null ? stat.getLostBalls() : 0));
            aggregatedStatistics.setLostBallsInOwnHalf(aggregatedStatistics.getLostBallsInOwnHalf() + (stat.getLostBallsInOwnHalf() != null ? stat.getLostBallsInOwnHalf() : 0));
            aggregatedStatistics.setGoalKicks(aggregatedStatistics.getGoalKicks() + (stat.getGoalKicks() != null ? stat.getGoalKicks() : 0));
        }

        return aggregatedStatistics;
    }
}
