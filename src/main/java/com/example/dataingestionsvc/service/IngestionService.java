package com.example.dataingestionsvc.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class IngestionService {

    @Autowired
    private AmazonSQS amazonSQS;

    @Value("${aws.sqs.patents-queue-url}")
    private String patentsQueueUrl;

    public void sendMsgToSqs(String message){
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(patentsQueueUrl)
                .withMessageBody(message)
                .withDelaySeconds(5);
        amazonSQS.sendMessage(send_msg_request);
    }



}
