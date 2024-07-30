// SubscriptionPack.java
package com.example.msusermanagement.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subscription_packs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String description;
}
