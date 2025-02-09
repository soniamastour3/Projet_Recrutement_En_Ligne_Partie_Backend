package com.example.candidatservice.Services.Impl;

import com.example.candidatservice.Dtos.request.CandidatRequest;
import com.example.candidatservice.Dtos.response.CandidatResponse;
import com.example.candidatservice.Modele.Candidat;
import com.example.candidatservice.Repository.CandidatDao;
import com.example.candidatservice.Services.CandidatService;
import com.example.candidatservice.Utils.StoresService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidatServiceIMPL implements CandidatService {
    public final CandidatDao candidatDaoconst;
    private final StoresService storesService;

    public CandidatServiceIMPL(CandidatDao candidatDaoconst, StoresService storesService) {
        this.candidatDaoconst = candidatDaoconst;
        this.storesService = storesService;
    }



    public CandidatResponse createcandidatwithimageandcv(CandidatRequest candidatRequest, MultipartFile  imageFile,  MultipartFile  cvFile) {
        Candidat c=CandidatResponse.toEntity(candidatRequest);
        c.setNom(candidatRequest.getNom());
        c.setPrenom(candidatRequest.getPrenom());
        c.setEmail(candidatRequest.getEmail());
        c.setPassword(candidatRequest.getPassword());
        // Store the file and set the paths
        if ( imageFile != null && ! imageFile.isEmpty()) {
            String storedFile = storesService.store(imageFile);
            c.setFile(storedFile);
        } else {
            System.out.println("File is null or empty!");
        }

        if (cvFile != null && !cvFile.isEmpty()) {
            String storedCv = storesService.store(cvFile);
            c.setCv(storedCv);
        } else {
            System.out.println("CV is null or empty!");
        }
        Candidat savedcandidat= candidatDaoconst.save(c);
        return CandidatResponse.fromEntity(savedcandidat);
    }
    @Override
    public CandidatResponse createcandidat(CandidatRequest candidat){
        Candidat c =CandidatResponse.toEntity(candidat);
        Candidat savedcandidat= candidatDaoconst.save(c);
        return CandidatResponse.fromEntity(savedcandidat);
    }

    @Override
    public List<CandidatResponse> allCandidat(){
        return candidatDaoconst.findAll().stream()
                .map(CandidatResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CandidatResponse candidatById(Long id) {
        return candidatDaoconst.findById(id)
                .map(CandidatResponse::fromEntity)
                .orElseThrow(() ->new RuntimeException("Candidat not found with this id:" +id));
    }
    @Override
    public CandidatResponse candidatByEmail(String email) {
        Candidat candidat = candidatDaoconst.findByEmail(email);
        if (candidat == null) {
            throw new RuntimeException("Candidat not found with this email: " + email);
        }
        return CandidatResponse.fromEntity(candidat);
    }

    @Override
    public CandidatResponse updatecandidat(CandidatRequest candidatRequest, Long id) {
        Candidat candidat=candidatDaoconst.findById(id).orElseThrow(()->
                new RuntimeException("Candidat not found with this id:" +id));
        if (candidat != null) {
            Candidat c = CandidatResponse.toEntity(candidatRequest);
            c.setIdCandidat(id);
            c.setNom(c.getNom() == null ? candidat.getNom() : c.getNom());
            Candidat savedcandidat = candidatDaoconst.save(c);
            return CandidatResponse.fromEntity(savedcandidat);
        }else{
            return null;
        }
    }


    @Override
    public HashMap<String, String> deletecandidat(Long id) {
        HashMap message =new HashMap<>();
        Candidat c = candidatDaoconst.findById(id).orElse(null);
        if (c != null) {
            try {
                candidatDaoconst.deleteById(id);
                message.put("message", "Candidat deleted successfully");
            }catch (Exception a){
                message.put("message", a.getMessage());
            }
        }else{
            message.put("message", "Candidat not found: " +id);
        }
        return message;
    }
}
