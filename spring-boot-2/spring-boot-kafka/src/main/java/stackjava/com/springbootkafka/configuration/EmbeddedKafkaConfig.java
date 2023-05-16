package stackjava.com.springbootkafka.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@Configuration
public class EmbeddedKafkaConfig {

    @Value("${kafka.host}")
    public String kafkaHost;
    @Bean
    public EmbeddedKafkaBroker broker() {
        if (!kafkaHost.contains("localhost")) {
            return null;
        }
        return new EmbeddedKafkaBroker(1)
                .kafkaPorts(9092)
                .brokerProperty("listeners", "PLAINTEXT://localhost:9092,REMOTE://localhost:9093")
                .brokerProperty("advertised.listeners", "PLAINTEXT://localhost:9092,REMOTE://localhost:9093")
                .brokerProperty("listener.security.protocol.map", "PLAINTEXT:PLAINTEXT,REMOTE:PLAINTEXT")
                .brokerListProperty("spring.kafka.bootstrap-servers");
    }

}
