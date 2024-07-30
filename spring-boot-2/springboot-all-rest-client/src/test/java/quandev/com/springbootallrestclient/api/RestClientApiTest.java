package quandev.com.springbootallrestclient.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestClientApiTest {

    @Autowired
    private RestClientApi restTemplateApi;

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