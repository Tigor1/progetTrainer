package ru.lid.progertrainer.config.s3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "s3")
public class S3ConfigProperties {
    String signatureType;
    String identityKeystoreFile;
    String identityKeystoreType;
    String identityKeystorePassword;
    String trustKeystoreFile;
    String trustKeystoreType;
    String trustKeystorePassword;
}
