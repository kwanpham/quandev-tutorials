package quandev.com.springbootallrestclient.api;

import feign.Request;
import feign.Response;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import quandev.com.springbootallrestclient.ResponseResult;

import java.time.Duration;

@Slf4j
@Component
public class RestClientApi {

    String bodyString =  "{\n" +
            "  \"name\": \"test\"\n" +
            "}";

    private  RestClient restClient;


    @PostConstruct
    public void init() {
        var factory = new HttpComponentsClientHttpRequestFactory();
        int timeout = 2;
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(timeout * 1000))
                .setResponseTimeout(Timeout.ofMilliseconds(timeout * 1000))
                .build();

        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        factory.setHttpClient(client);
        this.restClient = RestClient.builder().requestFactory(factory).build();
    }

    public void testSuccess() {
        // Call the testSuccess controller using RestClient
        try  {
            ResponseEntity<ResponseResult> responseEntity = restClient.post()
                    .uri("http://localhost:8080/testSuccess")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bodyString)
                    .retrieve()
                    .toEntity(ResponseResult.class);
            ResponseResult responseResult = responseEntity.getBody();
            log.info(responseResult.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void testFailed() {
        // Call the testFailed controller using RestClient
        try  {
            ResponseEntity<ResponseResult> responseEntity = restClient.post()
                    .uri("http://localhost:8080/testFailed")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bodyString)
                    .retrieve()
                    .toEntity(ResponseResult.class);
            ResponseResult responseResult = responseEntity.getBody();
            log.info(responseResult.toString());
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    public void testTimeout() {
        // Call the testTimeout controller using RestClient
        try  {
            ResponseEntity<ResponseResult> responseEntity = restClient.post()
                    .uri("http://localhost:8080/testTimeout")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bodyString)
                    .retrieve()
                    .toEntity(ResponseResult.class);
            ResponseResult responseResult = responseEntity.getBody();
            log.info(responseResult.toString());
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    public void testFailedConnection() {
        // Call the testTimeout controller using RestClient
        try  {
            ResponseEntity<ResponseResult> responseEntity = restClient.post()
                    .uri("http://localhost:8085/testTimeout")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bodyString)
                    .retrieve()
                    .toEntity(ResponseResult.class);
            ResponseResult responseResult = responseEntity.getBody();
            log.info(responseResult.toString());
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }
}