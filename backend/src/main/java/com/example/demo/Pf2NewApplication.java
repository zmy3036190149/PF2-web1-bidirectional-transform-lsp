package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Pf2NewApplication {

	public static void main(String[] args) {
//		SpringApplication.run(Pf2NewApplication.class, args);
	SpringApplicationBuilder builder = new SpringApplicationBuilder(Pf2NewApplication.class);
	builder.headless(false).run(args);
	}
}

