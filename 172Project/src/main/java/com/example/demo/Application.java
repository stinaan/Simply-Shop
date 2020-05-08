package com.example.demo;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableJpaRepositories("com.example.repository")
@EnableAutoConfiguration
@ComponentScan(basePackages="com.example")

public class Application {
	//Print message when application is run on docker; go help from https://www.youtube.com/watch?v=e3YERpG2rMs at 3:17
	@GetMapping("/docker")
	public String getMessage()
	{
		return "If you see this, docker worked";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
	}

}
