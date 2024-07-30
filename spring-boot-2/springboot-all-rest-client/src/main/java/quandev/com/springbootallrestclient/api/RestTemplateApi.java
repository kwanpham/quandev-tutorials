package quandev.com.springbootallrestclient.api;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import quandev.com.springbootallrestclient.ResponseResult;

import java.time.Duration;

@Slf4j
@Component
public class RestTemplateApi {

    String body =  "{\n" +
            "  \"name\": \"test\"\n" +
            "}";

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(1))
                .setReadTimeout(Duration.ofSeconds(3))
                .build();
    }


    public void testSuccess() {
        // Call the testSuccess controller using RestTemplate
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<ResponseResult> response = restTemplate.exchange("http://localhost:8080/testSuccess", HttpMethod.POST, request, ResponseResult.class);
            log.info(response.getBody().toString());
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }


    }


    public void testFailed() {
        // Call the testFailed controller using RestTemplate
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<ResponseResult> response = restTemplate.exchange("http://localhost:8080/testFailed", HttpMethod.POST, request, ResponseResult.class);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }


    public void testTimeout() {
        // Call the testTimeout controller using RestTemplate
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<ResponseResult> response = restTemplate.exchange("http://localhost:8080/testTimeout", HttpMethod.POST, request, ResponseResult.class);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    public void testFailedConnection() {
        // Call the testTimeout controller using RestTemplate
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<ResponseResult> response = restTemplate.exchange("http://localhost:8015/testTimeout", HttpMethod.POST, request, ResponseResult.class);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

}
