package com.example.msusermanagement.Controllers;

import com.example.msusermanagement.Entities.Subscription;
import com.example.msusermanagement.Entities.User;
import com.example.msusermanagement.Repositories.UserRepository;

import com.example.msusermanagement.Services.SubscriptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionServiceImpl subscriptionService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Subscription> listSubscriptions() {
        return subscriptionService.listSubscriptions();
    }

    @PostMapping

    public ResponseEntity<Subscription> addSubscription(
            @RequestParam("packId") Long packId,
            @RequestParam("teamName") String teamName,
            @RequestParam("country") String country,
            @RequestParam("position") String position
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription subscription = subscriptionService.addSubscription(packId, user.getId(), teamName, country,position);
        return ResponseEntity.ok(subscription);
    }



    @PutMapping("/{id}")
    public Subscription updateSubscription(@PathVariable Long id, @RequestBody Subscription subscription) {
        return subscriptionService.updateSubscription(id, subscription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userS-subscriptions")
    public List<com.example.msusermanagement.dto.SubscriptionUserDTO> UserSubscriptions() {
        return subscriptionService.listUserSubscriptions();
    }
    @GetMapping("/user-subscriptions")
    public List<com.example.msusermanagement.dto.SubscriptionUserDTO> listUserSubscriptions() {
        // Retrieve the currently authenticated user's username
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        // Retrieve subscriptions for the current user
        return subscriptionService.UserSubscriptions(currentUsername);
    }
    @PutMapping("/{subscriptionId}/change-pack")
    public Subscription changeSubscriptionPack(@PathVariable Long subscriptionId, @RequestParam Long packId) {
        return subscriptionService.updateSubscriptionPack(subscriptionId, packId);
    }
    @GetMapping("/has-active-subscription")
    public ResponseEntity<Boolean> hasActiveSubscription(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Directly return the hasSubscription attribute
        return ResponseEntity.ok(user.getHasSubscription());
    }
    @PutMapping("/{id}/toggle-active")
    public ResponseEntity<Subscription> toggleActiveStatus(@PathVariable Long id) {
        Subscription updatedSubscription = subscriptionService.toggleActiveStatus(id);
        return ResponseEntity.ok(updatedSubscription);
    }
    @DeleteMapping("/delete-all-subscriptions")
    public ResponseEntity<Void> deleteSubscriptionsForCurrentUser() {
        // Appeler la méthode du service pour supprimer toutes les souscriptions de l'utilisateur connecté
        subscriptionService.deleteSubscriptionsForCurrentUser();
        return ResponseEntity.noContent().build();
    }
}
