package com.example.application;

import com.example.application.rest_controller.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.example.application.rest_controller"},
		basePackageClasses = RestController.class)
public class SportsManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsManagementSystemApplication.class, args);
	}

}
