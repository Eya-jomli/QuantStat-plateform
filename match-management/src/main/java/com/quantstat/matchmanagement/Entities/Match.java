package com.quantstat.matchmanagement.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long matchId;
    private LocalDate date;
    private String location;
    private String score;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    @JsonBackReference(value="homeTeam")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    @JsonBackReference(value="awayTeam")
    private Team awayTeam;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MatchStatistic> matchStatistics;
}
