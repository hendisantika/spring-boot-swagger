package com.hendisantika.springboot.swagger;

import com.hendisantika.springboot.swagger.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@SpringBootTest(
		properties = {
				"management.endpoint.health.show-details=always",
				"spring.datasource.url=jdbc:tc:mysql:8.4.0:///bankDB"
		},
		webEnvironment = RANDOM_PORT
)
public class SpringbootSwaggerApplicationTests {

	@Autowired
	private StudentService studentService;

	@BeforeEach
	void deleteAll() {
		studentService.getAll();
	}

}
