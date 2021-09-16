package com.personal.dataingestionsvc.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.util.StringUtils;
import com.personal.dataingestionsvc.entity.PatentDetails;
import com.personal.dataingestionsvc.repository.PatentDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class IngestionService {

    @Autowired
    private AmazonSQS amazonSQS;

    @Value("${aws.sqs.patents-queue-url}")
    private String patentsQueueUrl;
    @Autowired
    private PatentDetailsRepository patentDetailsRepository;


    /**
     * Send Application numbers to SQS
     * Reject entire batch if even 1 does not comply for syntax("USXXXXXXXX")
     * Check data-retrival-svc for business logic for processing
     * @param message
     */
    public void sendMsgToSqs(String message) {
        log.info("Publishing message to SQS");
        if(StringUtils.isNullOrEmpty(message)){
            throw new RuntimeException("Please input valid Application numbers :"+  message);
        }
        if(message.contains(",")){
            sendBatch(message);
            return;
        }

        sendMessageAndPersistDetails(message);

        log.info("Finished publishing message to SQS");
    }

    private void sendBatch(String message) {
        log.info("Multiple Application numbers are found");
        String[] applicationNums = message.split(",");
        for(String appNum: applicationNums){
            sendMessageAndPersistDetails(appNum);
        }
        log.info("Finished publishing {} messages to SQS", applicationNums.length);

    }

    private void sendMessageAndPersistDetails(String message) {
        if(isValidMsg(message)){
            if(getPatentDetails(message) == null){
                SendMessageRequest send_msg_request = new SendMessageRequest()
                        .withQueueUrl(patentsQueueUrl)
                        .withMessageBody(message);
                // .withDelaySeconds(5);
                amazonSQS.sendMessage(send_msg_request);
                persistPatentDetails(message,"QUEUED");
            }

        } else{
            log.error("Invalid msg sent:{}", message);
            persistPatentDetails(message,"INVALID-MSG");
            throw new RuntimeException("Invalid msg sent :"+ message);
        }

    }

    /**
     *
     * @param message
     * @return true if length is 8 .. Prefix is US and all others characters must be digits
     */
    private static boolean isValidMsg(String message){

        if(!message.toUpperCase().startsWith("US")){
            return false;
        }
        if (message.length() != 10){
            return false;
        }

       String stripped =  message.substring(2);
        try {
            Double.parseDouble(stripped);
            return true;
        } catch(NumberFormatException e){
            return false;
        }


    }
    private void persistPatentDetails(String message, String workflowStaus) {
        PatentDetails patentDetails = new PatentDetails();
        patentDetails.setPatentApplicationNumber(message);
        patentDetails.setWorkflowStatus(workflowStaus);
        updateDetails(patentDetails, new Date(), new Date());
        patentDetailsRepository.save(patentDetails);
    }

    protected PatentDetails updateDetails(PatentDetails patentDetails, Date created, Date updated) {
        patentDetails.setCreatedDate(created);
        patentDetails.setUpdatedDate(updated);
        return patentDetails;
    }

    public PatentDetails getPatentDetails(String applicationNumber) {
        PatentDetails patentDetails =patentDetailsRepository.findByPatentApplicationNumber(applicationNumber);
        if(patentDetails == null){
            return null;
        }
        return patentDetails;
    }


}
