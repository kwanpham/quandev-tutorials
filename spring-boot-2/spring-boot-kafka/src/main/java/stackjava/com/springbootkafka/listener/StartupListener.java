package stackjava.com.springbootkafka.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StartupListener implements ApplicationRunner {



    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
