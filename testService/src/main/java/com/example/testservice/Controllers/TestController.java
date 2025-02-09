package com.example.testservice.Controllers;

import com.example.testservice.Dtos.request.TestRequest;
import com.example.testservice.Dtos.response.TestResponse;
import com.example.testservice.Services.TestService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {
    public final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/save")
    public TestResponse addTest(@RequestBody TestRequest testRequest) {
        return testService.createcategory(testRequest);
    }

    @GetMapping("/all")
    public List<TestResponse> AllTest() {
        return testService.allTest();
    }

    @GetMapping("/getone/{id}")
    public TestResponse testbyid(@PathVariable Long id) {
        return testService.testById(id);
    }

    @PutMapping("/update/{id}")
    public TestResponse updatetest(@RequestBody TestRequest testRequest, @PathVariable Long id) {
        return testService.updatetest(testRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deletetest(@PathVariable Long id) {
        return testService.deletetest(id);
    }


}
