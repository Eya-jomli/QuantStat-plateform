package com.example.msusermanagement.Controllers;


import com.example.msusermanagement.Services.FootballService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/user/data")
public class FootballController {

    @Autowired
    private FootballService footballService;

    @GetMapping("/teams")
    public String getTeams(@RequestParam String country) {
        return footballService.getTeamsByCountry(country);
    }

    @GetMapping("/countries")
    public String getCountries() {
        return footballService.getAllCountries();
    }

    @GetMapping("/team")
    public ResponseEntity<?> getTeamByName(@RequestParam String teamName) {
        String response = footballService.getTeamByName(teamName);
        return ResponseEntity.ok(response); // Return the response directly
    }

}
