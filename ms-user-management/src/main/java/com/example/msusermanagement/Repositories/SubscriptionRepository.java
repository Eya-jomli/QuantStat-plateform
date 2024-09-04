package com.example.msusermanagement.Repositories;

import com.example.msusermanagement.Entities.Pack;
import com.example.msusermanagement.Entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByUserId(Long userId);

    boolean existsByUserIdAndActiveTrue(Long userId);
    List<Subscription> findByUserUsername(String username);
    List<Subscription> findByUserId(Long userId);
}

