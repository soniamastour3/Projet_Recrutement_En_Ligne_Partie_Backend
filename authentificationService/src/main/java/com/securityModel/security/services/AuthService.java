
package com.securityModel.security.services;

import com.securityModel.OpenfeignClientAuth.AdministrateurRestClient;
import com.securityModel.payload.request.*;
import com.securityModel.payload.response.CandidatResponse;
import com.securityModel.payload.response.JwtResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import com.securityModel.OpenfeignClientAuth.CandidatRestClient;
import com.securityModel.Utils.StoresService;
import com.securityModel.exception.TokenRefreshException;

import com.securityModel.models.*;
import com.securityModel.payload.response.MessageResponse;
import com.securityModel.payload.response.TokenRefreshResponse;

import com.securityModel.repository.RoleRepository;

import com.securityModel.repository.UserRepository;
import com.securityModel.security.jwt.JwtUtils;

import feign.FeignException;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

//    @Autowired
//    EmailService emailService;

    @Autowired
    private JavaMailSender emailSender;

    ;
    @Autowired
    AdministrateurRestClient administrateurRestClient;
    @Autowired
    CandidatRestClient candidatRestClient;
    @Autowired
    StoresService storesService;



    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getNom(), loginRequest.getPassword()));
        Optional<User> u = userRepository.findByNom(loginRequest.getNom());
        if (u.get().isConfirm() == true) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(userDetails);

            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

            return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                    userDetails.getNom(), userDetails.getPrenom(), userDetails.getEmail(), roles));
        } else {
            throw new RuntimeException("user not confirm");
        }
    }
//
//    public JwtResponse authenticateAdmin(LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        Optional<User> u = userRepository.findByUsername(loginRequest.getUsername());
//        if (u.isPresent() && u.get() instanceof Admin admin) {
//
//            // Debugging: Log the confirmation status
//            System.out.println("Provider confirmation status: " + provider.isConfirm());
//
//            if (provider.isConfirm() == true) {
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//            String jwt = jwtUtils.generateJwtToken(userDetails);
//
//            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//                    .collect(Collectors.toList());
//
//            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
//
//            return new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(),
//                    userDetails.getEmail(), roles);
//        } else {
//            throw new RuntimeException("Provider not confirm");
//        }} else {
//            throw new RuntimeException("Provider not found.");
//        }
//    }

//    public JwtResponse authenticateCustomer(LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        Optional<User> u = userRepository.findByUsername(loginRequest.getUsername());
//        if (u.isPresent() && u.get() instanceof Customer customer) {
//
//            // Debugging: Log the confirmation status
//            System.out.println("Customer confirmation status: " + customer.isConfirm());
//
//            if (customer.isConfirm() == true) {
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//                String jwt = jwtUtils.generateJwtToken(userDetails);
//
//                List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//                        .collect(Collectors.toList());
//
//                RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
//
//                return new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(),
//                        userDetails.getEmail(), roles);
//            } else {
//                throw new RuntimeException("Customer not confirm");
//            }} else {
//            throw new RuntimeException("Customer not found.");
//        }
//    }
//    public MessageResponse registerUser(SignupRequest signUpRequest) throws MessagingException {
//        if (userRepository.existsByNom(signUpRequest.getNom())) {
//            return new MessageResponse("Error: Nom is already taken!");
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return new MessageResponse("Error: Email is already in use!");
//        }
//
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//        User user = new User();
//
//        // Assign roles and create Provider or Customer
////        if (strRoles != null && strRoles.contains("ROLE_PROVIDER")) {
////            user = new Provider(signUpRequest.getUsername(), signUpRequest.getEmail(),
////                    encoder.encode(signUpRequest.getPassword()), signUpRequest.getCompany());
////            roles.add(roleRepository.findByName(ERole.ROLE_PROVIDER)
////                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
//         if (strRoles != null && strRoles.contains("ROLE_CANDIDAT")) {
//            user = new CandidatRequest (signUpRequest.getNom(), signUpRequest.getPrenom(), signUpRequest.getEmail(),
//                    encoder.encode(signUpRequest.getPassword()));
//            roles.add(roleRepository.findByName(ERole.ROLE_CANDIDAT)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));}
////        } else {
////            user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
////                    encoder.encode(signUpRequest.getPassword()));
////            roles.add(roleRepository.findByName(ERole.ROLE_USER)
////                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
////        }
//
//        user.setConfirm(false); // Ensure user is not confirmed initially
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        sendConfirmationEmail(signUpRequest);
//
//        return new MessageResponse("User registered successfully!");
//    }

    public MessageResponse registerAdmin(SignupRequest signUpRequest) {
        // Création de l'administrateur
        AdministrateurRequest administrateurRequest = new AdministrateurRequest(
                signUpRequest.getNom(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );

        // Configuration des rôles
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        administrateurRequest.setRoles(roles);

        // Appel du service Feign
        try {
            administrateurRestClient.addAdministrateur(administrateurRequest);
        } catch (FeignException e) {
            return new MessageResponse("Failed to register admin: " + e.getMessage());
        }

        return new MessageResponse("Administrateur registered successfully!");
    }

//    public MessageResponse registerCandidat(SignupRequest signupRequest) {
//        try {
//
//
//
//            // Préparer les rôles
//            Set<Role> roles = new HashSet<>();
//            Role candidatRole = roleRepository.findByName(ERole.ROLE_CANDIDAT)
//                    .orElseThrow(() -> new RuntimeException("Erreur : rôle introuvable."));
//            roles.add(candidatRole);
//
//            // Convertir les rôles en noms (String)
//            List<String> roleNames = roles.stream()
//                    .map(role -> role.getName().name()) // `role.getName()` doit retourner un `ERole`
//                    .collect(Collectors.toList());
//
//            // Construire le DTO
//            CandidatRequest candidatRequest = new CandidatRequest();
//
//            candidatRequest.setNom(signupRequest.getNom());
//            candidatRequest.setPrenom(signupRequest.getPrenom());
//            candidatRequest.setEmail(signupRequest.getEmail());
//            candidatRequest.setPassword(encoder.encode(signupRequest.getPassword()));
//
//            candidatRequest.setRoles(roleNames);
//            candidatRequest.setConfirm(false);
//
//            // Appel du client Feign
//            candidatRestClient.addCandidat(candidatRequest);
//
//            // Envoyer un email de confirmation
//            //sendConfirmationEmailCandidat(signupRequest);
//
//            return new MessageResponse("Candidat enregistré avec succès !");
//        } catch (FeignException e) {
//            return new MessageResponse("Échec de l'enregistrement du candidat : " + e.getMessage());
//        }
//
//    }

    public void sendConfirmationEmail(SignupRequest SignupRequest) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("Complete Registration");
        String from="admin@gmail.com";
        String to= SignupRequest.getEmail();
        helper.setFrom(from);
        helper.setTo(to);
        String emailContent= ("<HTML><body><a href=\"http://localhost:9090/AUTHENTIFICATIONSERVICE/api/auth/confirm?email="+SignupRequest.getEmail()
                +"\">Confirm</a></body></HTML>");
        helper.setText(emailContent, true);
        emailSender.send(message);}
//       /* helper.setFrom("soniamastour.sm@gmail.com");
//        helper.setTo(user.getEmail());
//        String confirmationUrl = "http://localhost:8088/api/auth/confirm?email=" + user.getEmail();
//        String emailContent = "<html><body><p>Click the link below to complete your registration:</p>" +
//                "<a href=\"" + confirmationUrl + "\">Confirm</a></body></html>";
//
//        helper.setText(emailContent, true);
//        emailSender.send(message);*/

    private void sendConfirmationEmailCandidat(SignupRequest signupRequest) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("Complete Registration");
        String from="admin@gmail.com";
        String to= signupRequest.getEmail();
        helper.setFrom(from);
        helper.setTo(to);
        String emailContent= ("<HTML><body><a href=\"http://localhost:9090/AUTHENTIFICATIONSERVICE/api/auth/confirmCandidat?email="+signupRequest.getEmail()
                +"\">Confirm</a></body></HTML>");
        helper.setText(emailContent, true);
        emailSender.send(message);}

//    private void sendConfirmationEmailCustomer(SignupRequest SignupRequest) throws MessagingException {
//        MimeMessage messageP = emailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(messageP);
//        helper.setSubject("Complete Registration");
//        String from="admin@gmail.com";
//        String to= SignupRequest.getEmail();
//        helper.setFrom(from);
//        helper.setTo(to);
//        String emailContent= ("<HTML><body><a href=\"http://localhost:8085/api/auth/confirmCustomer?email="+SignupRequest.getEmail()
//                +"\">Confirm</a></body></HTML>");
//        helper.setText(emailContent, true);
//        emailSender.send(messageP);}

    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getNom());
                    return new TokenRefreshResponse(token, requestRefreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    public MessageResponse confirmUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user.isConfirm()) {
            return new MessageResponse("User is already confirmed.");
        }
        user.setConfirm(true);
        userRepository.save(user);
        return new MessageResponse("User confirmed successfully!");
    }
public MessageResponse confirmCandidat(String email) {
    CandidatResponse candidatResponse = candidatRestClient.candidatbyemail(email);
    System.out.println("Candidat Response: " + candidatResponse);

    if (candidatResponse.isConfirm()) {
        return new MessageResponse("Candidat is already confirmed.");
    }

    candidatResponse.setConfirm(true);



    // Créer le CandidatRequest avec les information's mises à jour
    CandidatRequest candidatRequest = new CandidatRequest();
    candidatRequest.setIdCandidat(candidatResponse.getIdCandidat());
    candidatRequest.setNom(candidatResponse.getNom());
    candidatRequest.setPrenom(candidatResponse.getPrenom());
    candidatRequest.setEmail(candidatResponse.getEmail());

    // Encoder le mot de passe
    String encodedPassword = encoder.encode(candidatResponse.getPassword());
    candidatRequest.setPassword(encodedPassword);
    candidatRequest.setFile(candidatResponse.getFile());
    candidatRequest.setCv(candidatResponse.getCv());
candidatRequest.setConfirm(candidatResponse.isConfirm());

    // Créer le candidat avec l'image (et le fichier)
    candidatRestClient.addCandidat(candidatRequest);

    return new MessageResponse("Candidat confirmed successfully!");
}
}

//    public MessageResponse confirmCustomer(String email) {
//        Customer customer = customerRepository.findByEmail(email);
//        if (customer.isConfirm()) {
//            return new MessageResponse("client is already confirmed.");
//        }
//        customer.setConfirm(true);
//        customerRepository.save(customer);
//        return new MessageResponse("client confirmed successfully!");
//    }



//    public MessageResponse logoutUser(UserDetailsImpl userDetails) {
//        Long userId = userDetails.getId();
//        refreshTokenService.deleteByUserId(userId);
//        return new MessageResponse("Log out successful!");
//    }
//
//    public MessageResponse logoutProvider(UserDetailsImpl userDetails) {
//        Long providerId = userDetails.getId(); // Get the provider's ID from the UserDetailsImpl
//        refreshTokenService.deleteByUserId(providerId); // Delete the refresh token for this provider
//        return new MessageResponse("Provider log out successful!"); // Return a success message
//    }
//
//    public MessageResponse logoutCustomer(UserDetailsImpl userDetails) {
//        Long CustomerId = userDetails.getId(); // Get the customer's ID from the UserDetailsImpl
//        refreshTokenService.deleteByUserId(CustomerId); // Delete the refresh token for this customer
//        return new MessageResponse("Customer log out successful!"); // Return a success message
//    }


