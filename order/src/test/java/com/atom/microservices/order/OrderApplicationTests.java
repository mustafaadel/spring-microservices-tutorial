package com.atom.microservices.order;

import com.atom.microservices.order.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;
import static org.hamcrest.MatcherAssert.assertThat;


// Step 1 : Random port for avoiding port conflict
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0) // Step 5 : AutoConfigureWireMock port 0 is random port
class OrderApplicationTests {
	//Step 2 : TestContainer for MySQL
	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	// Step 3 : Get the port of the MySQL container
	@LocalServerPort
	private int port;

	// Step 4 : Setup
	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}


	@Test
	void shouldPlaceOrder() {
		String orderRequest = "{\n" +
				"    \"skuCode\": \"iphone_15\",\n" +
				"    \"price\": 100,\n" +
				"    \"quantity\": 2\n" +
				"}";
		InventoryClientStub.stubInventoryCall("iphone_15", 2);
		InventoryClientStub.stubInventoryUpdate("iphone_15", 2);
		var response = RestAssured.given()
				.contentType("application/json")
				.body(orderRequest)
				.when()
				.post("/api/v1/order")
				.then()
				.log()
				.all()
				.statusCode(201)
				.extract()
				.response()
				.asString();

		assertThat(response, Matchers.equalTo("Order placed successfully"));
	}

}
