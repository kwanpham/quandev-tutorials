package stackjava.com.springbootkafka.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class KafkaMessage {

    private int id;

    private String name;

    private String address;

    private String phoneNumber;

    private int age;

    private long time;

}