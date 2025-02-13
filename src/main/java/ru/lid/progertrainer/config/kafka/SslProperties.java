package ru.lid.progertrainer.config.kafka;

import lombok.Data;

@Data
public class SslProperties {
    private boolean enabled;
    private String keyPassword;
    private StoreConfig truststore;
    private StoreConfig keystore;
}
