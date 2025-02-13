package ru.lid.progertrainer.config.s3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "s3.concrete-s3")
@Data
public class ConcreteS3Properties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketDelimiter;
    private String bucketIn;
    private String bucketInDir;
    private String bucketOut;
    private String bucketOutDir;
    private String bucketArchivedDir;
    private String bucketErrorDir;
    private long bucketCleanTtl;
    private long bucketCleanDelay;
    private String region;
    private String filenameIn;
    private String filenameOut;
    private boolean certCheck;
}
