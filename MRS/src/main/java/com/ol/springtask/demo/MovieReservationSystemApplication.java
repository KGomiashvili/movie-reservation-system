package com.ol.springtask.demo;

import com.ol.springtask.demo.entity.User;
import com.ol.springtask.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.ol.springtask.demo.entity.Role.ROLE_ADMIN;

@EnableScheduling
@SpringBootApplication
public class MovieReservationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieReservationSystemApplication.class, args);
	}

}

