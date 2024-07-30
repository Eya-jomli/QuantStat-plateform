package com.quantstat.matchmanagement.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class FootballService {

    private final String apiKey = "3"; // Utilisez votre clé API gratuite
    private final String apiUrl = "https://www.thesportsdb.com/api/v1/json/" + apiKey + "/search_all_teams.php?s=Soccer&c=COUNTRY_NAME";

    public String getTeamsByCountry(String country) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl.replace("COUNTRY_NAME", country), HttpMethod.GET, entity, String.class);
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Headers: " + response.getHeaders());
            System.out.println("Body: " + response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.err.println("Erreur HTTP : " + e.getStatusCode());
            System.err.println("Message : " + e.getResponseBodyAsString());
            return "Erreur : " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur inconnue : " + e.getMessage();
        }
    }
}
