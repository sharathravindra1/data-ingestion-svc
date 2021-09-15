package com.example.dataingestionsvc;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${aws.credentials.region}")
    private String awsRegion;
    @Value("${aws.credentials.accessKey}")
    private String awsAccessKey;
    @Value("${aws.credentials.secretKey}")
    private String awsSecretKey;

    @Bean
    public AmazonSQS amazonSQS() {
        log.info("Setting up sqs client");
        log.info(awsAccessKey);
        log.info(awsSecretKey);
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        AmazonSQS amazonSQS =  AmazonSQSClientBuilder
                .standard()
                .withRegion(Regions.fromName(awsRegion))
                .withRequestHandlers()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();
        log.info("Finished setting up sqs client");
        return amazonSQS;

    }

}
