package ru.lid.progertrainer.config.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public class S3StreamReader {
    private final AmazonS3 s3Client;
    private final long bufferSize;

    public S3StreamReader(AmazonS3 s3Client, long bufferSize) {
        this.s3Client = s3Client;
        this.bufferSize = bufferSize;
    }

    public Optional<InputStream> get(String bucketName, String key) {
        long totalSize = getSize(bucketName, key);

        Enumeration<S3ObjectInputStream> s3Enumeration = getEnumeration(bucketName, key, totalSize);
        return Optional.of(new SequenceInputStream(s3Enumeration));
    }

    private long getSize(String bucketName, String key) {
        return s3Client
                .getObjectMetadata(bucketName, key)
                .getContentLength();
    }

    private Enumeration<S3ObjectInputStream> getEnumeration(String bucketName, String key, long totalSize) {
        return new Enumeration<>() {
            private long currentPosition = 0;

            @Override
            public boolean hasMoreElements() {
                return currentPosition < totalSize;
            }

            @Override
            public S3ObjectInputStream nextElement() {
                if (!hasMoreElements()) {
                    throw new NoSuchElementException("No more elements in the stream.");
                }

                long minLength = Math.min(bufferSize, totalSize);

                GetObjectRequest getRequest = new GetObjectRequest(bucketName, key)
                        .withRange(currentPosition, currentPosition + minLength - 1);

                currentPosition += minLength;

                return s3Client.getObject(getRequest).getObjectContent();
            }
        };
    }
}
