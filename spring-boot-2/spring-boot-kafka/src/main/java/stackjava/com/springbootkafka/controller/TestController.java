//package stackjava.com.springbootkafka.controller;
//
//import com.github.javafaker.Faker;
//import org.apache.commons.lang3.time.StopWatch;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import stackjava.com.springbootkafka.dto.KafkaMessage;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//public class TestController {
//
//    @Autowired
//    private KafkaTemplate<String, KafkaMessage> kafkaTemplateJson;
//
//    @Autowired
//    private KafkaTemplate<String, KafkaMessageProtobuf> kafkaTemplateProto;
//
//
//    @GetMapping("/test1")
//    public String test1() {
//
//        Faker faker = new Faker();
//
//        List<KafkaMessage> kafkaMessageList = new ArrayList<>();
//
//        for (int i = 0; i <= 100000; i++) {
//            KafkaMessage kafkaMessage = new KafkaMessage()
//                    .setId(i)
//                    .setName(faker.funnyName().name())
//                    .setAddress(faker.address().fullAddress())
//                    .setPhoneNumber(faker.phoneNumber().cellPhone())
//                    .setAge(faker.random().nextInt(99));
//            kafkaMessageList.add(kafkaMessage);
//        }
//
//        StopWatch stopWatch = StopWatch.createStarted();
//        for (KafkaMessage kafkaMessage : kafkaMessageList) {
//            kafkaTemplateJson.send("testJson", kafkaMessage);
//        }
//        stopWatch.stop();
//
//        System.out.println(stopWatch);
//
//        return "ok";
//    }
//
//    @GetMapping("/testProtobuf")
//    public String test2() {
//        Faker faker = new Faker();
//        List<KafkaMessageProtobuf> kafkaMessageList = new ArrayList<>();
//
//        for (int i = 0; i <= 100000; i++) {
//            KafkaMessageProtobuf simpleMessage = KafkaMessageProtobuf.newBuilder()
//                    .setId(i)
//                    .setName(faker.funnyName().name())
//                    .setAddress(faker.address().fullAddress())
//                    .setPhoneNumber(faker.phoneNumber().cellPhone())
//                    .setAge(faker.random().nextInt(99))
//                    .build();
//
//            kafkaMessageList.add(simpleMessage);
//        }
//
//        StopWatch stopWatch = new StopWatch("send protobuf");
//        stopWatch.start();
//        for (KafkaMessageProtobuf kafkaMessage : kafkaMessageList) {
//            kafkaTemplateProto.send("testProtobuf", kafkaMessage);
//        }
//        stopWatch.stop();
//        System.out.println(stopWatch);
//        return "ok";
//    }
//
//}