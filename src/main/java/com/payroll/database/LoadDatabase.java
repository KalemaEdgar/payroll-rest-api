package com.payroll.database;

/**
 * This file is loaded automatically by Spring to preload data into the database
 * What happens when it gets loaded?
 **** Spring Boot will run ALL CommandLineRunner beans once the application context is loaded.
 **** This runner will request a copy of the EmployeeRepository you just created.
 **** Using the repository, it will create two entities and store them.
 */

import com.payroll.Employee;
import com.payroll.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Kalema Edgar", "CIO")));
            log.info("Preloading " + repository.save(new Employee("Regina Kalema", "Chief Executive")));
        };
    }
}
