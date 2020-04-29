package com.example.demo;

import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.*;

@Configuration
@ImportResource("src/main/resources/aws-config.xml")
//may need to change this
@EnableRdsInstance(databaseName = "userdb", 
//insert database name
                   dbInstanceIdentifier = "cmpe172database", 
                   //db instance identifier here
                   password = "thomas172")
//rds password here
public class AwsResourceConfig {
	
	//The AwsResourceConfig class handles configuration required for integration with S3 storage and the MySQL 
	//instance running on RDS. The contents of this class are explained in detail below.
	
	//ALSO EDIT aws-config.xml under resources
	//In order to access protected resources using Amazons SDK an access key and a secret key must be supplied. 
	//Spring Cloud for AWS provides an XML namespace for configuring both values so that they are available to the 
	//SDK at runtime.
	//but we can just hardcode it in since its just for a project
	
}
