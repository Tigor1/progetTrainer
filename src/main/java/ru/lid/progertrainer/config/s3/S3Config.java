package ru.lid.progertrainer.config.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "s3.enabled", havingValue = "true")
public class S3Config {

    private final S3ConfigProperties s3ConfigProperties;
    private final ConcreteS3Properties s3Properties;

    @Bean
    AmazonS3 amazonS3() {

        AWSCredentials credentials = new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey());

        System.setProperty(
                SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY,
                String.valueOf(s3Properties.isCertCheck())
        );

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride(s3ConfigProperties.signatureType);

        KeyStore identityKeyStore = null;
        KeyStore trustKeyStore = null;
        SSLContext sslContext = null;
        SSLConnectionSocketFactory sslConnectionSocketFactory = null;

        try {
            identityKeyStore = KeyStore.getInstance(s3ConfigProperties.getIdentityKeystoreType());
            FileInputStream identityKeyStoreFile = new FileInputStream(s3ConfigProperties.getIdentityKeystoreFile());
            identityKeyStore.load(identityKeyStoreFile, s3ConfigProperties.getIdentityKeystorePassword().toCharArray());
        } catch (Exception e) {
            log.error("Error while loading identity keystore file: {}, error: {}, detail: {}", s3ConfigProperties.getIdentityKeystoreFile(), e.getClass().getSimpleName(), e.getMessage());
        }

        try {
            trustKeyStore = KeyStore.getInstance(s3ConfigProperties.getTrustKeystoreType());
            FileInputStream trustKeyStoreFile = new FileInputStream(s3ConfigProperties.getTrustKeystoreFile());
            trustKeyStore.load(trustKeyStoreFile, s3ConfigProperties.getTrustKeystorePassword().toCharArray());
        } catch (Exception e) {
            log.error("Error while loading trust keystore file: {}, error: {}, detail: {}", s3ConfigProperties.getIdentityKeystoreFile(), e.getClass().getSimpleName(), e.getMessage());
        }


        if (identityKeyStore != null && trustKeyStore != null) {

            try {
                sslContext = SSLContextBuilder.create()
                        .loadTrustMaterial(trustKeyStore, new TrustStrategy() {

                            @Override
                            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                                return true;
                            }

                        })
                        .loadKeyMaterial(identityKeyStore, s3ConfigProperties.getIdentityKeystorePassword().toCharArray())
                        .build();
            } catch (Exception e) {
                log.error("Error while creating ssl context, cause: {}", e.getMessage());
            }

        }

        if (sslContext != null) {

            sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new String[] {"TLSv1.1", "TLSv1.2", "TLSv1.3"}, null, NoopHostnameVerifier.INSTANCE);
            clientConfiguration.getApacheHttpClientConfig().withSslSocketFactory(sslConnectionSocketFactory);

            return AmazonS3ClientBuilder
                    .standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Properties.getEndpoint(), s3Properties.getRegion()))
                    .withPathStyleAccessEnabled(true)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withClientConfiguration(clientConfiguration)
                    .build();

        }

        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Properties.getEndpoint(), s3Properties.getRegion()))
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
