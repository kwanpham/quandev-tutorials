package stackjava.com.springbootkafka.comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stackjava.com.springbootkafka.configuration.CustomKafkaMessage;

import java.util.Date;

@Component
public class Send {


    @Autowired
    private KafkaTemplate<String, CustomKafkaMessage> kafkaTemplate;



    @Scheduled(initialDelay = 8000 , fixedDelay = 100000000)
    public void send(){
        for (int i = 0; i < 120; i++) {

            CustomKafkaMessage customKafkaMessage = new CustomKafkaMessage();
            customKafkaMessage.setTransId(i +"");
            customKafkaMessage.setCurr(System.currentTimeMillis());

            kafkaTemplate.send("demo", customKafkaMessage);

        }
    }


}
