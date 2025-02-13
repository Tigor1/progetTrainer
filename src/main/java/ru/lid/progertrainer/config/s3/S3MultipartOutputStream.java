package ru.lid.progertrainer.config.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class S3MultipartOutputStream extends OutputStream {

    private final int bufferSize;

    private final AmazonS3 s3Client;
    private final String bucketName;
    private final String keyName;
    private final String uploadId;
    private final ByteArrayOutputStream buffer;
    private final List<PartETag> partETags;
    private int partNumber;

    public S3MultipartOutputStream(AmazonS3 s3Client, String bucketName, String keyName, int bufferSize) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.keyName = keyName;
        this.uploadId = initiateMultipartUpload();
        this.buffer = new ByteArrayOutputStream();
        this.partETags = new ArrayList<>();
        this.partNumber = 1;
        this.bufferSize = bufferSize;
    }

    @Override
    public void write(int b) {
        buffer.write(b);
        if (buffer.size() >= bufferSize) {
            uploadPart();
        }
    }

    @Override
    public void write(byte[] b, int off, int len) {
        while (len > 0) {
            int toWrite = Math.min(len, bufferSize - buffer.size());
            buffer.write(b, off, toWrite);
            if (buffer.size() >= bufferSize) {
                uploadPart();
            }
            off += toWrite;
            len -= toWrite;
        }
    }

    @Override
    public synchronized void flush() {
        if (buffer.size() > 0) {
            uploadPart();
        }
    }

    @Override
    public void close() {
        flush();
        completeMultipartUpload();
    }

    private String initiateMultipartUpload() {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, keyName);
        InitiateMultipartUploadResult result = s3Client.initiateMultipartUpload(request);
        return result.getUploadId();
    }

    private void uploadPart() {
        try {
            byte[] data = buffer.toByteArray();
            UploadPartRequest uploadRequest = new UploadPartRequest()
                    .withBucketName(bucketName)
                    .withKey(keyName)
                    .withUploadId(uploadId)
                    .withPartNumber(partNumber++)
                    .withInputStream(new ByteArrayInputStream(data))
                    .withPartSize(data.length);

            UploadPartResult uploadResult = s3Client.uploadPart(uploadRequest);
            partETags.add(uploadResult.getPartETag());
            buffer.reset();
        } catch (Exception e) {
            abortMultipartUpload();
            log.error("Failed to upload part", e);
        }
    }

    private void completeMultipartUpload() {
        try {
            CompleteMultipartUploadRequest completeRequest = new CompleteMultipartUploadRequest(
                    bucketName, keyName, uploadId, partETags);
            s3Client.completeMultipartUpload(completeRequest);
        } catch (Exception e) {
            abortMultipartUpload();
            log.error("Failed to complete multipart upload", e);
        }
    }

    private void abortMultipartUpload() {
        try {
            s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, keyName, uploadId));
        } catch (Exception e) {
            log.error("Failed to abort multipart upload", e);
        }
    }
}
