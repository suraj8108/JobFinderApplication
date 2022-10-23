package com.sprint.naukri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.ControllerAdvice;

@SpringBootApplication(scanBasePackages = {"com.controller","com.sprint.naukri","com.service","com.dto"})
@EnableJpaRepositories({"com.dao"})
@EntityScan(basePackages = {"com.model"})
public class NaukriApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaukriApplication.class, args);
	}

}
