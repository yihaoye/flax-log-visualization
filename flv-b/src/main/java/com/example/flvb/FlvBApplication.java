package com.example.flvb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FlvBApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlvBApplication.class, args);
	}

	@GetMapping("/renew")
	public String renew() {
		return String.format("Renewing!");
	}

	@GetMapping("/api/file")
	public String api(@RequestParam(value = "name", defaultValue = "test") String name) {
		return String.format("Getting file %s.json!", name);
	}
}
