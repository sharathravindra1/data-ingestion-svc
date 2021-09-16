package com.personal.dataingestionsvc;

import com.amazonaws.auth.BasicAWSCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Base64;

@SpringBootApplication
@Slf4j
public class DataIngestionSvcApplication {
    @Value("${aws.credentials.accessKey}")
    private String awsAccessKey;
    @Value("${aws.credentials.secretKey}")
    private String awsSecretKey;

    public static void main(String[] args) {
        SpringApplication.run(DataIngestionSvcApplication.class, args);
    }

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(awsAccessKey,awsSecretKey);

    }

}
