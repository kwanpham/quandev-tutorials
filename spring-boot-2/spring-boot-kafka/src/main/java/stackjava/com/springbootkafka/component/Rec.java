package stackjava.com.springbootkafka.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import stackjava.com.springbootkafka.dto.KafkaMessage;

@Slf4j
@Component
public class Rec {

//    @KafkaListener(topics = "demo3", groupId = "group-id", concurrency = "100")
//    public void listen(KafkaMessage message , Acknowledgment acknowledgment) {
//        acknowledgment.acknowledge();
//        long b = message.getTime();
//        b = ( System.currentTimeMillis() -b ) / 1000;
//        log.info(Thread.currentThread().toString() + "  Received Message : " + b + " ----- " + message.getId());
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }


    @KafkaListener(topics = "demo2", groupId = "group-id")
    public void listen2(KafkaMessage message ,  Acknowledgment acknowledgment) throws InterruptedException {
      //  acknowledgment.acknowledge();
        log.info("Start " + message.toString());
        Thread.sleep(5000);
        log.info("End  " + message);
    }

}
