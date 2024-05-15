package com.example.demo;

import com.example.demo.resources.HarvestedResource;
import com.example.demo.resources.PlantedResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@Configuration
public class DemoApplication extends ResourceConfig {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public DemoApplication() {
		register(PlantedResource.class);
		register(HarvestedResource.class);
	}

}
