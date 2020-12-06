package com.payroll.database;

/**
 * This file is loaded automatically by Spring to preload data into the database
 * What happens when it gets loaded?
 **** Spring Boot will run ALL CommandLineRunner beans once the application context is loaded.
 **** This runner will request a copy of the EmployeeRepository you just created.
 **** Using the repository, it will create two entities and store them.
 */

import com.payroll.Employee;
import com.payroll.Order;
import com.payroll.repository.EmployeeRepository;
import com.payroll.repository.OrderRepository;
import com.payroll.resources.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        return args -> {
            employeeRepository.save(new Employee("Kalema", "Edgar", "CIO"));
            employeeRepository.save(new Employee("Regina", "Kalema", "Chief Executive"));

            employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));

            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

            // for (Order order : orderRepository.findAll()) {
            //     log.info("Preloaded " + order);
            // }
            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    }
}
