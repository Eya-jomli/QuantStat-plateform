package com.example.msusermanagement.Services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
@Service
public class FootballService {

    private final String apiKey = "3"; // Utilisez votre clé API gratuite
    private final String apiUrl = "https://www.thesportsdb.com/api/v1/json/" + apiKey;

    public String getTeamsByCountry(String country) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/search_all_teams.php?s=Soccer&c=" + country, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            return "Erreur : " + e.getMessage();
        } catch (Exception e) {
            return "Erreur inconnue : " + e.getMessage();
        }
    }

    public String getAllCountries() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/all_countries.php", HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            return "Erreur : " + e.getMessage();
        } catch (Exception e) {
            return "Erreur inconnue : " + e.getMessage();
        }
    }

    // Nouvelle méthode pour rechercher une équipe par nom
    public String getTeamByName(String teamName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/searchteams.php?t=" + teamName, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            return "Erreur : " + e.getMessage();
        } catch (Exception e) {
            return "Erreur inconnue : " + e.getMessage();
        }
    }
}
