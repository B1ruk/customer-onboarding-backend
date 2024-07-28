package io.b1ruk.start.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
public class S3Service {

    private AmazonS3 amazonS3;

    private SqsService sqsService;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3Service(AmazonS3 amazonS3, SqsService sqsService) {
        this.amazonS3 = amazonS3;
        this.sqsService = sqsService;
    }

    public void uploadFile(File file) {
        var key = String.format("%s-%s", file.getName(), LocalDateTime.now());

        try {
            uploadFile(key, file);
        } catch (Exception e) {
            log.error("unable to parse input stream", e);
        }
    }


    public void uploadFile(String keyName, File file) {
        try {
            var result = amazonS3.putObject(bucketName, keyName, file);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("unable to upload file", e);
        }
    }
}
