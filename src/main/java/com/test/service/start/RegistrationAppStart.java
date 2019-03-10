package com.test.service.start;

import com.test.service.exclusion.ExclusionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Random;


@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.test.service.*"})
@EntityScan(basePackages = {"com.test.service.*"})
@EnableJpaRepositories("com.test.service.repository")
@SpringBootApplication
public class RegistrationAppStart {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationAppStart.class, args);
	}

	/**
	 * dummy {@link ExclusionService} implementation.
	 */
	@Bean
	public ExclusionService createExclusionService() {
		return ((dob, ssn) -> {return new Random().nextBoolean();});
	}

}
