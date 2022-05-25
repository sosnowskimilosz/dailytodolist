package com.milo.dailytodolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DailyToDoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyToDoListApplication.class, args);
	}

}
