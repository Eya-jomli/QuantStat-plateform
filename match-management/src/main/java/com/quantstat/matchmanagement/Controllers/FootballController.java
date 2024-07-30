package com.quantstat.matchmanagement.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.quantstat.matchmanagement.Services.FootballService;

@RestController
public class FootballController {

    @Autowired
    private FootballService footballService;

    @GetMapping("/teams")
    public String getTeams(@RequestParam String country) {
        return footballService.getTeamsByCountry(country);
    }
}
