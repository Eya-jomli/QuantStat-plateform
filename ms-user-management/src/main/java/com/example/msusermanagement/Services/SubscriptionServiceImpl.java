package com.example.msusermanagement.Services;

import com.example.msusermanagement.Entities.Pack;
import com.example.msusermanagement.Entities.Subscription;
import com.example.msusermanagement.Entities.User;
import com.example.msusermanagement.Repositories.PackRepository;
import com.example.msusermanagement.Repositories.SubscriptionRepository;
import com.example.msusermanagement.Repositories.UserRepository;
import com.example.msusermanagement.dto.SubscriptionUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Subscription> listSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription addSubscription(Long packId, Long userId, String teamName, String country,String position) {
        // Récupérer l'utilisateur par son ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Vérifier si l'utilisateur a déjà une souscription, active ou non
        if (hasSubscription(userId)) {
            throw new RuntimeException("You already have a subscription. You cannot subscribe to another pack.");
        }

        // Récupérer le pack par son ID
        Pack pack = packRepository.findById(packId)
                .orElseThrow(() -> new RuntimeException("Pack not found"));

        // Créer un nouvel abonnement
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPack(pack);
        subscription.setEquipeName(teamName);
        subscription.setPaysEquipe(country);
        subscription.setPosition(position);
        subscription.setStartDate(null);  // Start date remains null
        subscription.setEndDate(null);    // End date remains null
        subscription.setActive(false);    // Ensure the subscription is inactive initially

        Subscription savedSubscription = subscriptionRepository.save(subscription);

        // Mettre à jour le statut de l'abonnement de l'utilisateur si nécessaire
        if (!user.getHasSubscription()) {
            user.setHasSubscription(true);
            userRepository.save(user);
        }

        // Envoyer une notification
        String notificationMessage = "New subscription created by user: " + user.getUsername();
        messagingTemplate.convertAndSend("/topic/notifications", notificationMessage);

        return savedSubscription;
    }




    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }

    public Subscription updateSubscription(Long id, Subscription subscription) {
        Subscription existingSubscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        existingSubscription.setStartDate(subscription.getStartDate());
        existingSubscription.setEndDate(subscription.getEndDate());

        return subscriptionRepository.save(existingSubscription);
    }

    public List<SubscriptionUserDTO> listUserSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();

        return subscriptions.stream().map(subscription -> {
            SubscriptionUserDTO dto = new SubscriptionUserDTO();
            dto.setId(subscription.getId());
            dto.setUsername(subscription.getUser().getUsername());
            dto.setPackName(subscription.getPack().getName());
            dto.setStartDate(subscription.getStartDate());
            dto.setEndDate(subscription.getEndDate());
            dto.setStatus(subscription.getEndDate() != null && subscription.getEndDate().isBefore(LocalDate.now()) ? "Expired" : "Renewable");
            dto.setEquipeName(subscription.getEquipeName());
            dto.setPaysEquipe(subscription.getPaysEquipe());
            dto.setActive(subscription.isActive());
            dto.setPosition(subscription.getPosition());
            return dto;
        }).collect(Collectors.toList());
    }
    public List<SubscriptionUserDTO> UserSubscriptions(String username) {
        // Fetch subscriptions only for the currently logged-in user
        List<Subscription> subscriptions = subscriptionRepository.findByUserUsername(username);

        return subscriptions.stream().map(subscription -> {
            SubscriptionUserDTO dto = new SubscriptionUserDTO();
            dto.setId(subscription.getId());
            dto.setUsername(subscription.getUser().getUsername());
            dto.setPackName(subscription.getPack().getName());
            dto.setStartDate(subscription.getStartDate());
            dto.setEndDate(subscription.getEndDate());
            dto.setStatus(subscription.getEndDate() != null && subscription.getEndDate().isBefore(LocalDate.now()) ? "Expired" : "Renewable");
            dto.setEquipeName(subscription.getEquipeName());
            dto.setPaysEquipe(subscription.getPaysEquipe());
            dto.setActive(subscription.isActive());
            dto.setPosition(subscription.getPosition());
            return dto;
        }).collect(Collectors.toList());
    }

        public Subscription updateSubscriptionPack(Long subscriptionId, Long packId) {
            Subscription subscription = subscriptionRepository.findById(subscriptionId)
                    .orElseThrow(() -> new RuntimeException("Subscription not found"));
            Pack pack = packRepository.findById(packId).orElseThrow(() -> new RuntimeException("Pack not found"));

            subscription.setPack(pack);
            subscription.setEndDate(LocalDate.now().plusDays(pack.getDuration()));

            return subscriptionRepository.save(subscription);
        }
    public boolean hasActiveSubscription(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getSubscriptions().stream()
                .anyMatch(subscription -> subscription.getEndDate().isAfter(LocalDate.now()));
    }
    public Subscription toggleActiveStatus(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        boolean newStatus = !subscription.isActive();
        subscription.setActive(newStatus);

        if (newStatus) { // If status is now true, activate the subscription
            LocalDate activationDate = LocalDate.now();
            subscription.setStartDate(activationDate);  // Set start date to today

            subscription.setEndDate(LocalDate.now().plusDays(subscription.getPack().getDuration()));
            String userEmail = subscription.getUser().getEmail();
            String subject = "Your Subscription is Now Active!";
            String message = "Dear " + subscription.getUser().getUsername() + ",\n\n"
                    + "Your subscription has been activated! You can now access your account and benefit from the latest statistics about your favorite team.\n\n"
                    + "Thank you for choosing our service.\n\n"
                    + "Best regards,\n"
                    + "The Team";

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);

            emailService.send(mailMessage);
        } else {
            // Optionally, handle deactivation logic here if needed
        }

        return subscriptionRepository.save(subscription);
    }
    public boolean hasSubscription(Long userId) {
        return subscriptionRepository.existsByUserId(userId);
    }

    public boolean hasActiveSubscription(Long userId) {
        return subscriptionRepository.existsByUserIdAndActiveTrue(userId);
    }

    @Transactional
    public void deleteSubscriptionsForCurrentUser() {
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Récupérer l'utilisateur par son nom d'utilisateur
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Rechercher toutes les souscriptions de l'utilisateur connecté
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(currentUser.getId());

        // Supprimer toutes les souscriptions trouvées
        subscriptionRepository.deleteAll(subscriptions);
    }

}



