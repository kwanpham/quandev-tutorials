package io.github.kwanpham.springmybatisdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public synchronized String get() throws InterruptedException {
        log.info("hear");
        Thread.sleep(10000);
        return "ok";
    }


}
