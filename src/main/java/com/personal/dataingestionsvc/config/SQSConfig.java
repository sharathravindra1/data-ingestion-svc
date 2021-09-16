package com.personal.dataingestionsvc.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Slf4j
public class SQSConfig {

    @Value("${aws.credentials.region}")
    private String awsRegion;
    @Autowired
    BasicAWSCredentials basicAWSCredentials;

    @Bean
    public AmazonSQS amazonSQS() {
        log.info("Setting up sqs client");
        AmazonSQS amazonSQS =  AmazonSQSClientBuilder
                .standard()
                .withRegion(Regions.fromName(awsRegion))
                .withRequestHandlers()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();
        log.info("Finished setting up sqs client");
        return amazonSQS;

    }

    public static void main(String[] args) {
        List<String> strs = Stream.of("a","b","c").collect(Collectors.toList());
        List<String> strs1 =null;
                String joined = strs1.stream().collect(Collectors.joining(","));

        System.out.println(joined);
    }
}
