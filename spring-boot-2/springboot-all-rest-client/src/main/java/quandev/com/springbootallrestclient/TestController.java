package quandev.com.springbootallrestclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {


    @PostMapping("/testSuccess")
    public ResponseEntity<ResponseResult> testSuccess(@RequestBody String body) {
        log.info("testSuccess: {}", body);
        return ResponseEntity.ok(new ResponseResult("00" , "success"));
    }

    @PostMapping("/testFailed")
    public ResponseEntity<ResponseResult> testFailed(@RequestBody String body) {
        log.info("testFailed: {}", body);
        return ResponseEntity.internalServerError().body(new ResponseResult("01" , "failed"));
    }

    @PostMapping("/testTimeout")
    public ResponseEntity<ResponseResult> testTimeout(@RequestBody String body) throws InterruptedException {
        log.info("testTimeout: {}", body);
        Thread.sleep(5000);
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new ResponseResult("68" , "timeout"));
    }

}
