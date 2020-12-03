package com.payroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Pulls in component scanning, autoconfiguration, and property support. It will fire up a servlet container and serve up our service.
public class PayrollRestfulApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayrollRestfulApiApplication.class, args);
	}

}
