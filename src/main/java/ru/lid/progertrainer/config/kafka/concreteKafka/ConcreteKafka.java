package ru.lid.progertrainer.config.kafka.concreteKafka;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.lid.progertrainer.config.kafka.KafkaBuilder;

@Configuration
@AllArgsConstructor
@ConditionalOnProperty(value = "kafka.concrete.enabled", havingValue = "true")
public class ConcreteKafka {
    private final ConcreteKafkaProperties concreteKafkaProperties;

    @Bean
    ConsumerFactory<String, String> kafkaConcreteFactory() {
        return KafkaBuilder.buildConsumerFactory(concreteKafkaProperties, String.class);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> concreteKafkaListenerContainerFactory(ConsumerFactory<String, String> kafkaConcreteFactory) {
        return KafkaBuilder.buildConcurrentKafkaListenerContainerFactory(kafkaConcreteFactory);
    }

    @Bean
    ProducerFactory<String, String> concreteKafkaProducerFactory(
            ObjectProvider<DefaultKafkaProducerFactoryCustomizer> customizers
    ) {
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(KafkaBuilder.buildProducerFactoryProperties(concreteKafkaProperties));
        customizers.orderedStream().forEach(customizer -> customizer.customize(producerFactory));
        return producerFactory;
    }

    @Bean
    KafkaTemplate<String, String> concreteKafkaTemplate(
            ProducerFactory<String, String> concreteKafkaProducerFactory
//                                                        ObjectProvider<String> messageConverter
    ) {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(concreteKafkaProducerFactory);
//        messageConverter.ifUnique(kafkaTemplate::setMessageConverter);

        return kafkaTemplate;
    }

}
