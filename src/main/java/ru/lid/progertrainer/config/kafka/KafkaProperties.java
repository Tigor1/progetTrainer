package ru.lid.progertrainer.config.kafka;

import lombok.Data;

@Data
public class KafkaProperties {
    private String bootstrapServers;
    private String consumerGroupId;
    private boolean enabled;
    private SslProperties ssl;
}
