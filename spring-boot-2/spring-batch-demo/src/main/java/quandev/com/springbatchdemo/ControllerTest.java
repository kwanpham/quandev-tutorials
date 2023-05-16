package quandev.com.springbatchdemo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class ControllerTest {


    @Autowired
    TaskExecutor batchTaskExecutor;
    @GetMapping("/")
    public String get() throws InterruptedException {
        batchTaskExecutor.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(6000);
                log.info("Run async");
            }
        });
        TimeUnit.SECONDS.sleep(15);
        return "OK";
    }


}
