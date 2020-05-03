package com.example.demo.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

//Create a new AmazonS3 client, got help from https://stackoverflow.com/questions/51034175/missing-amazons3client-bean-when-upgrading-springcloud-finchley-to-release
@Configuration
public class S3Config {

    @Bean
    public static AmazonS3Client amazonS3Client() {
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
        		//Add region, got help from https://stackoverflow.com/questions/44151982/aws-java-sdk-unable-to-find-a-region-via-the-region-provider-chain
        		.withRegion("us-east-1")
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }
}