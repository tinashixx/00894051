package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class SrpingjmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SrpingjmsApplication.class, args);
	}

}
