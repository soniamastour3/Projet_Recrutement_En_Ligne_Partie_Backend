package com.example.candidatservice.Services;

import com.example.candidatservice.Dtos.request.CandidatRequest;
import com.example.candidatservice.Dtos.response.CandidatResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface CandidatService {
    CandidatResponse createcandidatwithimageandcv(CandidatRequest candidatRequest, MultipartFile file,  MultipartFile cv);
        CandidatResponse createcandidat(CandidatRequest candidat);
    List<CandidatResponse> allCandidat();
    CandidatResponse candidatById(Long id);
    CandidatResponse updatecandidat(CandidatRequest candidatRequest, Long id);
    HashMap<String, String> deletecandidat(Long id);
    CandidatResponse candidatByEmail(String email);
}
