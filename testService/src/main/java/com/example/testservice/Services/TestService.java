package com.example.testservice.Services;

import com.example.testservice.Dtos.request.TestRequest;
import com.example.testservice.Dtos.response.TestResponse;

import java.util.HashMap;
import java.util.List;

public interface TestService {
    TestResponse createcategory(TestRequest test);
    List<TestResponse> allTest();
    TestResponse testById(Long id);
    TestResponse updatetest(TestRequest testRequest, Long id);
    HashMap<String, String> deletetest(Long id);
}
