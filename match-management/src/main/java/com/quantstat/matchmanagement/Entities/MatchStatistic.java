package com.quantstat.matchmanagement.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@Entity
public class MatchStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    @JsonBackReference
    private Match match;

    private int index;
    private Integer goals;
    private Integer goalsConceded;
    private Double goalConversionPercentage;
    private Integer fouls;
    private Integer yellowCards;
    private Integer redCards;
    private String formation;
    private Integer corners;
    private Integer shots;
    private Integer shotsOnTarget;
    private Integer entrancesToOpponentsHalf;
    private Integer entrancesToOpponentsBox;
    private Integer lostBalls;
    private Integer lostBallsInOwnHalf;
    private Integer goalKicks;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;
}
