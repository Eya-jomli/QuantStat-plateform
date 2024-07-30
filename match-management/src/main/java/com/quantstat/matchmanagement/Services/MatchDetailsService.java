package com.quantstat.matchmanagement.Services;



import com.quantstat.matchmanagement.DTO.MatchDetailsDTO;
import com.quantstat.matchmanagement.DTO.MatchStatisticDTO;
import com.quantstat.matchmanagement.Entities.Match;
import com.quantstat.matchmanagement.Repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchDetailsService {
    private final MatchRepository matchRepository;

    @Autowired
    public MatchDetailsService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<MatchDetailsDTO> getAllMatchDetails() {
        List<Match> matches = matchRepository.findAll();

        return matches.stream().map(match -> {
            MatchDetailsDTO matchDetailsDTO = new MatchDetailsDTO();
            matchDetailsDTO.setMatchId(match.getId());
            matchDetailsDTO.setDate(match.getDate());
            matchDetailsDTO.setLocation(match.getLocation());

            List<MatchStatisticDTO> statistics = match.getMatchStatistics().stream().map(stat -> {
                MatchStatisticDTO dto = new MatchStatisticDTO();
                dto.setTeamName(stat.getTeam().getName());
                dto.setGoals(stat.getGoals());
                dto.setGoalsConceded(stat.getGoalsConceded());
                dto.setGoalConversionPercentage(stat.getGoalConversionPercentage());
                dto.setFouls(stat.getFouls());
                dto.setYellowCards(stat.getYellowCards());
                dto.setRedCards(stat.getRedCards());
                dto.setFormation(stat.getFormation());
                dto.setCorners(stat.getCorners());
                dto.setShots(stat.getShots());
                dto.setShotsOnTarget(stat.getShotsOnTarget());
                dto.setEntrancesToOpponentsHalf(stat.getEntrancesToOpponentsHalf());
                dto.setEntrancesToOpponentsBox(stat.getEntrancesToOpponentsBox());
                dto.setLostBalls(stat.getLostBalls());
                dto.setLostBallsInOwnHalf(stat.getLostBallsInOwnHalf());
                dto.setGoalKicks(stat.getGoalKicks());
                return dto;
            }).collect(Collectors.toList());

            matchDetailsDTO.setStatistics(statistics);

            return matchDetailsDTO;
        }).collect(Collectors.toList());
    }
}
