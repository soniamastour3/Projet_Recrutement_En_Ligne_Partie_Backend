package com.example.administrateurservice.Services.IMPL;

import com.example.administrateurservice.Dtos.request.AdministrateurRequest;
import com.example.administrateurservice.Dtos.response.AdministrateurResponse;
import com.example.administrateurservice.Modele.Administrateur;
import com.example.administrateurservice.Repository.AdministrateurDao;
import com.example.administrateurservice.Services.AdministrateurService;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdministrateurServiceIMPL implements AdministrateurService  {
    public final AdministrateurDao administrateurDaoconst;



    private PasswordEncoder encoder;




    private JavaMailSender emailSender;









    public AdministrateurServiceIMPL(AdministrateurDao administrateurDaoconst) {
        this.administrateurDaoconst = administrateurDaoconst;

    }
    @Override
    public AdministrateurResponse createadministrateur(AdministrateurRequest administrateur){
        Administrateur a =AdministrateurResponse.toEntity(administrateur);
        Administrateur savedadministrateur= administrateurDaoconst.save(a);
        return AdministrateurResponse.fromEntity(savedadministrateur);
    }
//    @Override
//    public ResponseEntity<?> registerAdmin(SignupRequest signUpRequest){
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        // Créer une nouvelle instance d'Administrateur
//        Administrateur administrateur = new Administrateur();
//        administrateur.setNom(signUpRequest.getUsername());
//        administrateur.setEmail(signUpRequest.getEmail());
//        administrateur.setPassword(encoder.encode(signUpRequest.getPassword()));
//
//        // Attribuer automatiquement le rôle ROLE_ADMIN
//        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                .orElseThrow(() -> new RuntimeException("Error: Role ADMIN is not found."));
//        Set<String> roles = new HashSet<>();
//        roles.add(adminRole.toString());
//
//
//
//        // Affecter les rôles au modèle
//        administrateur.setRoles(roles);
//
//
//
//        // Enregistrer l'Administrateur dans la base de données
//        administrateurDaoconst.save(administrateur);
//
//        // Retourner un message de succès
//        return ResponseEntity.ok(new MessageResponse("Admin registered successfully!"));
//    }
    @Override
    public List<AdministrateurResponse> allAdministrateur(){
        return administrateurDaoconst.findAll().stream()
                .map(AdministrateurResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AdministrateurResponse administrateurById(Long id) {
        return administrateurDaoconst.findById(id)
                .map(AdministrateurResponse::fromEntity)
                .orElseThrow(() ->new RuntimeException("Administrateur not found with this id:" +id));
    }

    @Override
    public AdministrateurResponse updateadministrateur(AdministrateurRequest administrateurRequest, Long id) {
        Administrateur administrateur=administrateurDaoconst.findById(id).orElseThrow(()->
                new RuntimeException("Administrateur not found with this id:" +id));
        if (administrateur != null) {
            Administrateur a = AdministrateurResponse.toEntity(administrateurRequest);
            a.setIdAdmin(id);
            a.setNom(a.getNom() == null ? administrateur.getNom() : a.getNom());
            Administrateur savedadministrateur = administrateurDaoconst.save(a);
            return AdministrateurResponse.fromEntity(savedadministrateur);
        }else{
            return null;
        }
    }


    @Override
    public HashMap<String, String> deleteadministrateur(Long id) {
        HashMap message =new HashMap<>();
        Administrateur ad = administrateurDaoconst.findById(id).orElse(null);
        if (ad != null) {
            try {
                administrateurDaoconst.deleteById(id);
                message.put("message", "Administrateur deleted successfully");
            }catch (Exception a){
                message.put("message", a.getMessage());
            }
        }else{
            message.put("message", "Administrateur not found: " +id);
        }
        return message;
    }
}
