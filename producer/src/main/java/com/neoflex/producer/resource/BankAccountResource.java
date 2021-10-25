package com.neoflex.producer.resource;

import com.neoflex.producer.model.BankAccount;
import com.neoflex.producer.service.KafkaProducerService;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@EnableScheduling
public class BankAccountResource {
    private final KafkaTemplate<UUID, BankAccount> KAFKA_TEMPLATE;
    private final String TOPIC;

    public BankAccountResource(KafkaTemplate<UUID, BankAccount> kafkaTemplate,
                               @Value(value = "${kafka.topic.name}") String topic) {
        this.KAFKA_TEMPLATE = kafkaTemplate;
        this.TOPIC = topic;
    }

    @Scheduled(fixedDelay = 60000)
    public void post() {
        List<BankAccount> list = KafkaProducerService.getService();
        for (BankAccount bankAccount : list) {
            KAFKA_TEMPLATE.send(TOPIC, bankAccount.getUuid(), bankAccount);
        }
    }

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(TOPIC, 3,(short) 1);
    }
}
