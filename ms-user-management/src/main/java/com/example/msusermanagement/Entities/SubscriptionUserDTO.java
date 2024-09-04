package com.example.msusermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionUserDTO {
    private Long id;
    private String username;
    private String packName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String equipeName;
    private String paysEquipe;
    private boolean active;
    private String position;

}
