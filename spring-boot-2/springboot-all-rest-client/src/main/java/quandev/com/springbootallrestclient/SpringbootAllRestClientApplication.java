package quandev.com.springbootallrestclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringbootAllRestClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAllRestClientApplication.class, args);
    }

}
