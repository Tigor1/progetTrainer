package ru.lid.progertrainer.config.kafka.concreteKafka;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.lid.progertrainer.config.kafka.KafkaProperties;

@Data
@Configuration
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "kafka.concrete")
public class ConcreteKafkaProperties extends KafkaProperties {
}
