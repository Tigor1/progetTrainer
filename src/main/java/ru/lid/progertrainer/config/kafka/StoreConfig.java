package ru.lid.progertrainer.config.kafka;

import lombok.Data;

@Data
public class StoreConfig {
    private String type;
    private String location;
    private String password;
}
