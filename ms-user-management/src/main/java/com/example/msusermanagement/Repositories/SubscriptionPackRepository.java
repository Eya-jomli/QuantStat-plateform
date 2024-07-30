// SubscriptionPackRepository.java
package com.example.msusermanagement.Repositories;

import com.example.msusermanagement.Entities.SubscriptionPack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionPackRepository extends JpaRepository<SubscriptionPack, Long> {}
