package com.example.msusermanagement.Security;

import com.example.msusermanagement.Entities.ConfirmationKey;
import com.example.msusermanagement.Entities.ERole;
import com.example.msusermanagement.Entities.Role;
import com.example.msusermanagement.Entities.User;
import com.example.msusermanagement.Repositories.ConfirmationKeyRepo;
import com.example.msusermanagement.Repositories.RoleRepository;
import com.example.msusermanagement.Repositories.UserRepository;
import com.example.msusermanagement.Services.EmailService;
import com.example.msusermanagement.error.BadRequestException;
import com.example.msusermanagement.payload.request.SignupRequest;
import com.example.msusermanagement.payload.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@Slf4j
public class SecurityController {
    /////autheeentication

    @Autowired
    EmailService emailService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private ConfirmationKeyRepo confirmationKeyRepo;


    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/reset-password")
    private String resetPassword(@RequestParam String confirmationKey, @RequestParam String newPassword){
        this.checkIfConfirmationKeyIsValid(confirmationKey);
        return this.resetPasswordAndDeleteConfirmationKey(confirmationKey, newPassword);
    }

    private void checkIfConfirmationKeyIsValid(String confirmationKey){
        if(!this.confirmationKeyRepo.existsByConfirmationKey(confirmationKey))
            throw new BadRequestException("ConfirmationKey invalid");
    }

    private String resetPasswordAndDeleteConfirmationKey(String confirmationKey, String newPassword){
        ConfirmationKey confirmationKey1 = this.confirmationKeyRepo.findByConfirmationKey(confirmationKey).get();
        User user = this.userRepository.findByEmail(confirmationKey1.getEmailAddress()).get();
        user.setPassword(this.encoder.encode(newPassword));
        this.userRepository.save(user);
        this.confirmationKeyRepo.delete(confirmationKey1);
        return "Password changed";
    }

    @PostMapping("/forgot-password")
    private String forgotPassword(@RequestParam String emailAddress) {
        if (!userRepository.existsByEmail(emailAddress)) {
            throw new BadRequestException("Email address invalid");
        }
        if (confirmationKeyRepo.existsByEmailAddress(emailAddress)) {
            throw new BadRequestException("We have already sent an email to reset your password");
        }
        final var key = UUID.randomUUID().toString();
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(emailAddress);
        simpleMailMessage.setSubject("Password reset");
        simpleMailMessage.setFrom("clasherwin59@gmail.com");
        simpleMailMessage.setText("To change your password add this confirmation Key : " +key);
        emailService.send(simpleMailMessage);
        return this.generateAndPersistConfirmationKey(emailAddress, key);
    }

    private String generateAndPersistConfirmationKey(String emailAddress, String key) {
        User user = this.userRepository.findByEmail(emailAddress).get();
        ConfirmationKey confirmationKey = new ConfirmationKey();
        confirmationKey.setEmailAddress(emailAddress);
        confirmationKey.setConfirmationKey(key);
        this.confirmationKeyRepo.save(confirmationKey);
        return "We have sent an email to reset your password";
    }



    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        log.info("helooooooooooooooooo");
        log.info(username);
        log.info(password);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        Instant instant = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(120, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope", scope)
                .build();
        JwtEncoderParameters jwtEncoderParameters =
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );
        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        //return Map.of("accessToken", jwt);
        return Map.of("accessToken", jwt, "role", scope);


    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getPhoneNumber(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = Set.of(signUpRequest.getRole());
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.admin)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.admin)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;

                    case "coach":
                        Role coachRole = roleRepository.findByName(ERole.coach)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(coachRole);
                        break;

                    case "scouter":
                        Role scouterRole = roleRepository.findByName(ERole.scouter)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(scouterRole);
                        break;



                    case "analyst":
                        Role analystRole = roleRepository.findByName(ERole.analyst)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(analystRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.player) // Assuming default role is "player"
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }


        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

