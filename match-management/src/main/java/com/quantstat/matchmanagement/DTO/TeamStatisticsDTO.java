package com.quantstat.matchmanagement.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamStatisticsDTO {
    private int goals;
    private int goalsConceded;
    private int fouls;
    private int yellowCards;
    private int redCards;
    private int corners;
    private int shots;
    private int shotsOnTarget;
    private int entrancesToOpponentsHalf;
    private int entrancesToOpponentsBox;
    private int lostBalls;
    private int lostBallsInOwnHalf;
    private int goalKicks;
}
