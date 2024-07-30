package com.quantstat.matchmanagement.DTO;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class MatchDetailsDTO {
    private Long matchId;
    private LocalDate date;
    private String location;
    private List<MatchStatisticDTO> statistics;


}
