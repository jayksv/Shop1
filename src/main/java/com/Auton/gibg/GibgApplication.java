package com.Auton.gibg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import com.Auton.gibg.config.SwaggerConfig;

@SpringBootApplication
public class GibgApplication {
//	private final static Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

	public static void main(String[] args) {
		SpringApplication.run(GibgApplication.class, args);
	}
}
