package com.example.testservice.Services.Impl;

import com.example.testservice.Dtos.request.TestRequest;
import com.example.testservice.Dtos.response.TestResponse;
import com.example.testservice.Modele.Test;
import com.example.testservice.Repository.TestDAO;
import com.example.testservice.Services.TestService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestServiceIMPL implements TestService {
    public static TestDAO testDaoconst;

    public TestServiceIMPL(TestDAO testDaoconst) {
        this.testDaoconst = testDaoconst;
    }

    @Override
    public TestResponse createcategory(TestRequest test){
        Test t =TestResponse.toEntity(test);
        Test savedtest= testDaoconst.save(t);
        return TestResponse.fromEntity(savedtest);
    }

    @Override
    public List<TestResponse> allTest(){
        return testDaoconst.findAll().stream()
                .map(TestResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TestResponse testById(Long id) {
        return testDaoconst.findById(id)
                .map(TestResponse::fromEntity)
                .orElseThrow(() ->new RuntimeException("Test not found with this id:" +id));
    }

    @Override
    public TestResponse updatetest(TestRequest testRequest, Long id) {
        Test test=testDaoconst.findById(id).orElseThrow(()->
                new RuntimeException("Test not found with this id:" +id));
        if (test != null) {
            Test t = TestResponse.toEntity(testRequest);
            t.setIdTest(id);
            t.setTitre(t.getTitre() == null ? test.getTitre() : t.getTitre());
            Test savedtest = testDaoconst.save(t);
            return TestResponse.fromEntity(savedtest);
        }else{
            return null;
        }
    }


    @Override
    public HashMap<String, String> deletetest(Long id) {
        HashMap message =new HashMap<>();
        Test t = testDaoconst.findById(id).orElse(null);
        if (t != null) {
            try {
                testDaoconst.deleteById(id);
                message.put("message", "Test deleted successfully");
            }catch (Exception a){
                message.put("message", a.getMessage());
            }
        }else{
            message.put("message", "Test not found: " +id);
        }
        return message;
    }
}
