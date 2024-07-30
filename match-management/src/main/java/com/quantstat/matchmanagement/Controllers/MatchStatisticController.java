package com.quantstat.matchmanagement.Controllers;

import com.quantstat.matchmanagement.DTO.TeamStatisticsDTO;
import com.quantstat.matchmanagement.Entities.MatchStatistic;
import com.quantstat.matchmanagement.Services.MatchStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-matches/matchStatistics")
public class MatchStatisticController {
    private final MatchStatisticService matchStatisticService;

    @Autowired
    public MatchStatisticController(MatchStatisticService matchStatisticService) {
        this.matchStatisticService = matchStatisticService;
    }

    @GetMapping
    public List<MatchStatistic> getAllMatchStatistics() {
        return matchStatisticService.getAllMatchStatistics();
    }

    @GetMapping("/{id}")
    public MatchStatistic getMatchStatisticById(@PathVariable Long id) {
        return matchStatisticService.getMatchStatisticById(id);
    }

    @PostMapping
    public MatchStatistic createMatchStatistic(@RequestBody MatchStatistic matchStatistic) {
        return matchStatisticService.saveMatchStatistic(matchStatistic);
    }

    @PutMapping("/{id}")
    public MatchStatistic updateMatchStatistic(@PathVariable Long id, @RequestBody MatchStatistic matchStatistic) {
        matchStatistic.setId(id);
        return matchStatisticService.saveMatchStatistic(matchStatistic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMatchStatistic(@PathVariable Long id) {
        matchStatisticService.deleteMatchStatistic(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/team/{teamId}")
    public List<MatchStatistic> getStatisticsByTeamId(@PathVariable Long teamId) {
        return matchStatisticService.getStatisticsByTeamId(teamId);
    }

    @GetMapping("/team/aggregated/{teamId}")
    public TeamStatisticsDTO getAggregatedStatisticsByTeamId(@PathVariable Long teamId) {
        return matchStatisticService.getAggregatedStatisticsByTeamId(teamId);
    }
}
