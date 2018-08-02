package com;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import com.business.RentalCarBusiness;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = { "com"})
public class RentalCarApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RentalCarApplication.class, args);
		try {
			context.getBean(RentalCarBusiness.class).obtainJsonFile();
		} catch (IOException e) {
			log.error("Something wrong occurred while trying to initiate the project", e.getStackTrace());
		}
	}
}
