package quandev.com.springbootallrestclient.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestTemplateApiTest {

    @Autowired
    private RestTemplateApi restTemplateApi;

    @Test
    void testSuccess() {
        restTemplateApi.testSuccess();
    }

    @Test
    void testFailed() {
        restTemplateApi.testFailed();
    }

    @Test
    void testTimeout() {
        restTemplateApi.testTimeout();
    }

    @Test
    void testConnect() {
        restTemplateApi.testFailedConnection();
    }
}