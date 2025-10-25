package com.web.ncumt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NcumtApplication {

	public static void main(String[] args) {
		SpringApplication.run(NcumtApplication.class, args);
	}

}
