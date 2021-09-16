package com.personal.dataingestionsvc.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.personal.dataingestionsvc.entity.PatentDetails;
import com.personal.dataingestionsvc.repository.PatentDetailsRepository;
import org.easymock.EasyMock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import java.util.Date;


public class IngestionServiceTest {
    IngestionService ingestionService = new IngestionService(){
        @Override
        protected PatentDetails updateDetails(PatentDetails patentDetails, Date created, Date updated) {
            patentDetails.setCreatedDate(null);
            patentDetails.setUpdatedDate(null);
            return patentDetails;
        }

    };



    @Test(description = "Ingestion service : Application exists")
    public void testIngestionServiceApplExists() {
        String patentsQueueUrl = "s3://dummyQueue";
        AmazonSQS amazonSQS = EasyMock.createMock(AmazonSQS.class);
        PatentDetailsRepository patentDetailsRepository = EasyMock.createMock(PatentDetailsRepository.class);

        ReflectionTestUtils.setField(ingestionService, "amazonSQS", amazonSQS);
        ReflectionTestUtils.setField(ingestionService, "patentDetailsRepository", patentDetailsRepository);
        ReflectionTestUtils.setField(ingestionService, "patentsQueueUrl", patentsQueueUrl);
        EasyMock.expect(amazonSQS.sendMessage(EasyMock.anyObject())).andReturn(EasyMock.anyObject());
        PatentDetails patentDetails = new PatentDetails();

        EasyMock.expect(patentDetailsRepository.findByPatentApplicationNumber("US12345678")).andReturn(patentDetails).times(1);
        EasyMock.replay(amazonSQS, patentDetailsRepository);
        ingestionService.sendMsgToSqs("US12345678");

    }

    @Test(description = "Ingestion service : No appl sent", expectedExceptions = RuntimeException.class)
    public void testIngestionServiceFailed_nullApplicationNumber() {
        String patentsQueueUrl = "s3://dummyQueue";
        AmazonSQS amazonSQS = EasyMock.createMock(AmazonSQS.class);
        PatentDetailsRepository patentDetailsRepository = EasyMock.createMock(PatentDetailsRepository.class);

        ReflectionTestUtils.setField(ingestionService, "amazonSQS", amazonSQS);
        ReflectionTestUtils.setField(ingestionService, "patentDetailsRepository", patentDetailsRepository);
        ReflectionTestUtils.setField(ingestionService, "patentsQueueUrl", patentsQueueUrl);
        ingestionService.sendMsgToSqs(null);

    }


    @Test(description = "Ingestion service Validation fail", expectedExceptions = RuntimeException.class)
    public void testIngestionServiceValidationFailed() {
        String patentsQueueUrl = "s3://dummyQueue";
        AmazonSQS amazonSQS = EasyMock.createMock(AmazonSQS.class);
        PatentDetailsRepository patentDetailsRepository = EasyMock.createMock(PatentDetailsRepository.class);

        ReflectionTestUtils.setField(ingestionService, "amazonSQS", amazonSQS);
        ReflectionTestUtils.setField(ingestionService, "patentDetailsRepository", patentDetailsRepository);
        ReflectionTestUtils.setField(ingestionService, "patentsQueueUrl", patentsQueueUrl);
        EasyMock.expect(patentDetailsRepository.save(getPatentDetails("US-12345678","INVALID-MSG"))).andReturn(new PatentDetails());
        EasyMock.replay(patentDetailsRepository);
        ingestionService.sendMsgToSqs("US-12345678");


    }

    @Test(description = "Ingestion service happy")
    public void testIngestionServiceHappy() {
        String patentsQueueUrl = "s3://dummyQueue";
        AmazonSQS amazonSQS = EasyMock.createMock(AmazonSQS.class);
        PatentDetailsRepository patentDetailsRepository = EasyMock.createMock(PatentDetailsRepository.class);
        ReflectionTestUtils.setField(ingestionService, "amazonSQS", amazonSQS);
        ReflectionTestUtils.setField(ingestionService, "patentDetailsRepository", patentDetailsRepository);
        ReflectionTestUtils.setField(ingestionService, "patentsQueueUrl", patentsQueueUrl);
        EasyMock.expect(patentDetailsRepository.findByPatentApplicationNumber("US12345678")).andReturn(null).anyTimes();
        EasyMock.expect(amazonSQS.sendMessage(EasyMock.anyObject())).andReturn(EasyMock.anyObject());

        EasyMock.expect(patentDetailsRepository.save(getPatentDetails("US12345678","QUEUED"))).andReturn(new PatentDetails());
        EasyMock.replay(amazonSQS, patentDetailsRepository);
        ingestionService.sendMsgToSqs("US12345678");
    }

    @Test(description = "Ingestion service Multiple happy")
    public void testIngestionServiceMultipleHappy() {
        String patentsQueueUrl = "s3://dummyQueue";
        AmazonSQS amazonSQS = EasyMock.createMock(AmazonSQS.class);
        PatentDetailsRepository patentDetailsRepository = EasyMock.createMock(PatentDetailsRepository.class);
        ReflectionTestUtils.setField(ingestionService, "amazonSQS", amazonSQS);
        ReflectionTestUtils.setField(ingestionService, "patentDetailsRepository", patentDetailsRepository);
        ReflectionTestUtils.setField(ingestionService, "patentsQueueUrl", patentsQueueUrl);

        PatentDetails patentDetails1 = getPatentDetails("US12345678","QUEUED");

        PatentDetails patentDetails2 = getPatentDetails("US12345679","QUEUED");
        EasyMock.expect(amazonSQS.sendMessage(EasyMock.anyObject())).andReturn(EasyMock.anyObject()).anyTimes();

        EasyMock.expect(patentDetailsRepository.findByPatentApplicationNumber("US12345678")).andReturn(null).times(1);
        EasyMock.expect(patentDetailsRepository.findByPatentApplicationNumber("US12345679")).andReturn(null).times(1);
        EasyMock.expect(patentDetailsRepository.save(patentDetails1)).andReturn(new PatentDetails());
        EasyMock.expect(patentDetailsRepository.save(patentDetails2)).andReturn(new PatentDetails());
        EasyMock.replay(amazonSQS, patentDetailsRepository);
        ingestionService.sendMsgToSqs("US12345678,US12345679");

    }

    private PatentDetails getPatentDetails(String appNum, String status) {
        PatentDetails patentDetails2 = new PatentDetails();
        patentDetails2.setPatentApplicationNumber(appNum);
        patentDetails2.setWorkflowStatus(status);
        return patentDetails2;
    }

}
