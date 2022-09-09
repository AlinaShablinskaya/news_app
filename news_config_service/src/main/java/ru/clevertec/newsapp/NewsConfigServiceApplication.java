package ru.clevertec.newsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class NewsConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsConfigServiceApplication.class, args);
	}
}
