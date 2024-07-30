package com.quantstat.matchmanagement.Controllers;

import com.quantstat.matchmanagement.DTO.MatchDetailsDTO;
import com.quantstat.matchmanagement.Services.MatchDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-matches/match-details")
public class MatchDetailsController {
    private final MatchDetailsService matchDetailsService;

    @Autowired
    public MatchDetailsController(MatchDetailsService matchDetailsService) {
        this.matchDetailsService = matchDetailsService;
    }

    @GetMapping
    public List<MatchDetailsDTO> getAllMatchDetails() {
        return matchDetailsService.getAllMatchDetails();
    }
}
