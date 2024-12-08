package com.adnancigtekin.saga.inventory.config;

import com.adnancigtekin.saga.event.inventory.ItemAllocationFailedEvent;
import com.adnancigtekin.saga.event.inventory.ItemAllocationSuccessEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {


    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, ItemAllocationSuccessEvent> itemAllocationSuccessProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);


        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    @Qualifier("itemAllocationSuccessTemplate")
    public KafkaTemplate<String, ItemAllocationSuccessEvent> kafkaItemAllocationSuccessTemplate() {
        return new KafkaTemplate<>(itemAllocationSuccessProducerFactory());
    }



    @Bean
    public ProducerFactory<String, ItemAllocationFailedEvent> itemAllocationFailedProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);


        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    @Qualifier("itemAllocationFailedTemplate")
    public KafkaTemplate<String, ItemAllocationFailedEvent> kafkaItemAllocationFailedTemplate() {
        return new KafkaTemplate<>(itemAllocationFailedProducerFactory());
    }


}
