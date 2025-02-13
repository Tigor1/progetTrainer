package ru.lid.progertrainer.config.kafka;


import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.MicrometerConsumerListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class KafkaBuilder {

    public static <T> ConsumerFactory<String, T> buildConsumerFactory(KafkaProperties properties, Class<T> messageType) {
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(messageType, JacksonUtils.enhancedObjectMapper());
        deserializer.addTrustedPackages("ru.lid.progertrainer.dto.*");
        deserializer.setUseTypeHeaders(false);

        HashMap<String, Object> propertiesMap = new HashMap<>();

        propertiesMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        propertiesMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        propertiesMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propertiesMap.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getConsumerGroupId());
        propertiesMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        propertiesMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.FALSE);
        addSslProperties(propertiesMap, properties);

        DefaultKafkaConsumerFactory<String, T> factory = new DefaultKafkaConsumerFactory<>(propertiesMap);
        factory.addListener(new MicrometerConsumerListener<>(Metrics.globalRegistry));
        return factory;
    }

    public static <T>ConcurrentKafkaListenerContainerFactory<String, T> buildConcurrentKafkaListenerContainerFactory(ConsumerFactory<String, T> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(1);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    public static Map<String, Object> buildProducerFactoryProperties(KafkaProperties properties) {
        Map<String, Object> propertiesMap = new HashMap<>();

        propertiesMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        propertiesMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        propertiesMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        propertiesMap.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, 120000);
        addSslProperties(propertiesMap, properties);

        return propertiesMap;
    }

    public static void addSslProperties(Map<String, Object> propertiesMap, KafkaProperties properties) {
        if (properties.getSsl().isEnabled()) {
            propertiesMap.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "SSL");
            propertiesMap.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, properties.getSsl().getKeyPassword());
            propertiesMap.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, properties.getSsl().getTruststore().getType());
            propertiesMap.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, properties.getSsl().getTruststore().getLocation());
            propertiesMap.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, properties.getSsl().getTruststore().getPassword());
            propertiesMap.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, properties.getSsl().getKeystore().getType());
            propertiesMap.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, properties.getSsl().getKeystore().getLocation());
            propertiesMap.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, properties.getSsl().getKeystore().getPassword());
        }
    }
}
