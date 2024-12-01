package com.springboot.news;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot News App",
				description = "Spring Boot News App REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Denys Slyva",
						email = "densuper2701@gmail.com",
						url = "https://github.com/noJusticeLike"
				)
		)
)
public class SpringbootNewsApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootNewsApplication.class, args);
	}
}
