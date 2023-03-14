package stackjava.com.springbootkafka.comp;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stackjava.com.springbootkafka.dto.KafkaMessage;

@Component
public class Send {


    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;



    @Scheduled(initialDelay = 8000 , fixedDelay = 100000000)
    public void send(){
        for (int i = 0; i < 10; i++) {

            KafkaMessage kafkaMessage = new KafkaMessage();
            kafkaMessage.setId(i);
            kafkaMessage.setName(RandomStringUtils.randomAlphanumeric(10));
            kafkaMessage.setTime(System.currentTimeMillis());
            kafkaTemplate.send("demo", kafkaMessage);

        }
    }


}
