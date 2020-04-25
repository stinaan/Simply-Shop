package com.example.demo;

import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.*;

@Configuration
@ImportResource("src/main/resources/aws-config.xml")
//may need to change this
@EnableRdsInstance(databaseName = "${database-name:}", 
//insert database name
                   dbInstanceIdentifier = "${db-instance-identifier:}", 
                   //db instance identifier here
                   password = "${rdsPassword:}")
//rds password here
public class AwsResourceConfig {
	
}
