package com.securityModel.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.securityModel.models.ERole;
import com.securityModel.models.Role;
import com.securityModel.models.User;
import com.securityModel.payload.request.LoginRequest;
import com.securityModel.payload.request.SignupRequest;
import com.securityModel.payload.request.TokenRefreshRequest;
import com.securityModel.payload.response.JwtResponse;
import com.securityModel.payload.response.MessageResponse;
import com.securityModel.payload.response.TokenRefreshResponse;
import com.securityModel.repository.RoleRepository;
import com.securityModel.repository.UserRepository;
import com.securityModel.security.jwt.JwtUtils;
import com.securityModel.security.services.AuthService;
import com.securityModel.security.services.RefreshTokenService;
import com.securityModel.security.services.UserDetailsImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.securityModel.exception.TokenRefreshException;
import com.securityModel.models.RefreshToken;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  AuthService authService;

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

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getNom(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        .collect(Collectors.toList());

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
        userDetails.getNom(),userDetails.getPrenom(), userDetails.getEmail(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException {
    if (userRepository.existsByNom(signUpRequest.getNom())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getNom(), signUpRequest.getPrenom(), signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null || strRoles.isEmpty()) {
      // Par défaut, attribuez un rôle ou retournez une erreur
      Role defaultRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(defaultRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "CANDIDAT":
            Role candidatRole = roleRepository.findByName(ERole.ROLE_CANDIDAT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(candidatRole);
            break;
          default:
            throw new RuntimeException("Error: Role is not recognized.");
        }
      });
    };

    user.setConfirm(false); // Ensure user is not confirmed initially
    user.setRoles(roles);
    userRepository.save(user);
    authService.sendConfirmationEmail(signUpRequest);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

//    @PostMapping("/signupUser")
//    public MessageResponse registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException {
//        return authService.registerUser(signUpRequest);
//    }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String token = jwtUtils.generateTokenFromUsername(user.getNom());
          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        })
        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
            "Refresh token is not in database!"));
  }
  @PostMapping("/signupAdmin")
  public ResponseEntity<?> signupAdmin (@Valid @RequestBody SignupRequest signupRequest)  {
    System.out.println("Requête reçue : " + signupRequest); // Log pour vérifier les données reçues
    MessageResponse messageResponse = authService.registerAdmin(signupRequest);
    return ResponseEntity.ok(messageResponse);
  }

//  @PostMapping("/signupCandidat")
//  public ResponseEntity<?> signupCandidat(
//          @RequestPart("signupRequest") SignupRequest signupRequest) {
//    System.out.println("Requête reçue : " + signupRequest); // Log pour vérifier les données reçues
//    MessageResponse messageResponse = authService.registerCandidat(signupRequest);
//    return ResponseEntity.ok(messageResponse);
//  }
  @GetMapping("/confirm")
  public ResponseEntity<?> confirmUser(
          @RequestParam String email) {
    MessageResponse messageResponse = authService.confirmUser(email);
    return ResponseEntity.ok(messageResponse);
  }
  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getId();
    refreshTokenService.deleteByUserId(userId);
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }

}
