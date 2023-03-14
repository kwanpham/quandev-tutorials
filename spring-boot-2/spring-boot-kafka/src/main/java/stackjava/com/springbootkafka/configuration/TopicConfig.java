package stackjava.com.springbootkafka.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TopicConfig {

    @Value("${kafka.host}")
    public String kafkaHost;

    @Bean
    public NewTopic generalTopic() {
        return TopicBuilder.name("demo")
                .partitions(100)
                .replicas(1)
                .build();
    }

    //If not using spring boot

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        return new KafkaAdmin(configs);
    }
}