package com.example.ClinicalDecisionSupportSystemService;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ClinicalDecisionSupportSystemServiceApplication {

	@Bean
	public WebClient webClient(){
		return WebClient.builder().build();
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(ClinicalDecisionSupportSystemServiceApplication.class, args);
	}

}
