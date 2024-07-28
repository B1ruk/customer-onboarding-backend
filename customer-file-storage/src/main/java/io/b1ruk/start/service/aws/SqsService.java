package io.b1ruk.start.service.aws;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SqsService {

    private final AmazonSQS amazonSqs;

    @Value("${aws.sqs.url}")
    private String sqsQueueUrl;

    public SqsService(AmazonSQS amazonSqs) {
        this.amazonSqs = amazonSqs;
    }

    public SendMessageResult publishMessage(String message,String groupId) {
        try {
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(sqsQueueUrl)
                    .withMessageBody(message)
                    .withMessageGroupId(groupId)
                    .withMessageDeduplicationId(UUID.randomUUID().toString());

            return amazonSqs.sendMessage(sendMessageRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
