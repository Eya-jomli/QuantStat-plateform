package com.quantstat.matchmanagement.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String league;
    private String country;

    @OneToMany(mappedBy = "homeTeam")
    @JsonManagedReference(value="homeTeam")
    private List<Match> homeMatches;

    @OneToMany(mappedBy = "awayTeam")
    @JsonManagedReference(value="awayTeam")
    private List<Match> awayMatches;
}
