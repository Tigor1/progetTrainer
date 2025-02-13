package ru.lid.progertrainer.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.DeleteVersionRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.VersionListing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.lid.progertrainer.config.s3.S3MultipartOutputStream;
import ru.lid.progertrainer.config.s3.S3StreamReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "s3.enabled", havingValue = "true")
public class S3Service {

    @Value("${s3.base2-like-to-ufx.buffer-size}")
    private int bufferSize;
    private final AmazonS3 s3Client;

    public void uploadFile(String bucketName, String fileName, File file) {
        s3Client.putObject(bucketName, fileName, file);
    }

    public InputStream downloadFile(String bucketName, String fileName) throws FileNotFoundException {
        S3StreamReader s3StreamReader = new S3StreamReader(s3Client, bufferSize);
        return s3StreamReader.get(bucketName, fileName)
                .orElseThrow(() -> new FileNotFoundException("File " + fileName + "not found in bucket " + bucketName));
    }


    public String getFileChecksum(String bucketName, String fileName) throws NoSuchAlgorithmException, IOException {
        S3StreamReader s3StreamReader = new S3StreamReader(s3Client, bufferSize);
        InputStream stream = s3StreamReader.get(bucketName, fileName).orElseThrow(() -> new FileNotFoundException("File " + fileName + "not found in bucket " + bucketName));
        byte[] hash = MessageDigest.getInstance("MD5").digest(stream.readAllBytes());
        return new BigInteger(1, hash).toString(16);
    }

    public List<S3ObjectSummary> getAllFiles(String bucketName) {
        return s3Client.listObjectsV2(bucketName)
                .getObjectSummaries();
    }

    public Collection<S3ObjectSummary> getAllFiles(String bucketName, String dir) {
        return s3Client.listObjectsV2(bucketName, dir).getObjectSummaries();
    }

    public S3MultipartOutputStream getOutputStream(String bucket, String fileName) {
        return new S3MultipartOutputStream(s3Client, bucket, fileName, bufferSize);
    }

    public void uploadFile(String bucketName, String key, byte[] data) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(data.length);
            metadata.setContentType("application/xml");
            s3Client.putObject(new PutObjectRequest(
                    bucketName,
                    key,
                    byteArrayInputStream,
                    metadata));
        } catch (IOException e) {
            log.error("Failed to load file {} in bucket {}, {}", key, bucketName, e.getMessage());
        }
    }

    public Map<String, S3ObjectSummary> deleteFiles(Map<String, S3ObjectSummary> files) {

        Map<String, S3ObjectSummary> deletedObjects = new HashedMap<>();
        for (Map.Entry<String, S3ObjectSummary> summary: files.entrySet()) {
            try {
                String bucketVersionStatus = s3Client.getBucketVersioningConfiguration(summary.getValue().getBucketName()).getStatus();
                if (BucketVersioningConfiguration.ENABLED.equals(bucketVersionStatus)) {
                    VersionListing versionListing = s3Client.listVersions(summary.getValue().getBucketName(), summary.getValue().getKey());
                    versionListing.getVersionSummaries().forEach(version -> {
                        s3Client.deleteVersion(new DeleteVersionRequest(summary.getValue().getBucketName(), summary.getValue().getKey(), version.getVersionId()));
                    });
                } else {
                    s3Client.deleteObject(summary.getValue().getBucketName(), summary.getValue().getKey());
                }
                deletedObjects.put(summary.getKey(), summary.getValue());
                log.info("the file {} from bucket {} has been deleted", summary.getValue().getKey(), summary.getValue().getBucketName());
            } catch (Exception e) {
                log.error("Error on deleting s3 object: bucket: {}, file: {}", summary.getValue().getBucketName(), summary.getValue().getKey());
            }
        }
        return deletedObjects;

    }
}
