package com.quantstat.matchmanagement.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MatchStatisticDTO {
    private String teamName;
    private Integer goals;
    private Integer goalsConceded;
    private Double goalConversionPercentage;
    private int fouls;
    private int yellowCards;
    private int redCards;
    private String formation;
    private int corners;
    private int shots;
    private int shotsOnTarget;
    private int entrancesToOpponentsHalf;
    private int entrancesToOpponentsBox;
    private int lostBalls;
    private int lostBallsInOwnHalf;
    private int goalKicks;


}
