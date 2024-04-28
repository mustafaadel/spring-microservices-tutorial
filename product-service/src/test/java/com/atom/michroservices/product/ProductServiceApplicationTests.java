package com.atom.michroservices.product;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.hamcrest.Matchers;
import org.testcontainers.utility.DockerImageName;

// Random port is used to avoid port conflicts
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongodbContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private int port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongodbContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = "{\n" +
				"  \"name\": \"Product 1\",\n" +
				"  \"description\": \"Product 1 Description\",\n" +
				"  \"price\": 100\n" +
				"}";
		RestAssured.given().contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/v1/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Product 1"))
				.body("description", Matchers.equalTo("Product 1 Description"))
				.body("price", Matchers.equalTo(100));
	}

}
