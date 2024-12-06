package edu.ada.grupo5.movies_api;


import org.junit.jupiter.api.BeforeEach;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.matchesPattern;

// Do not forget to initialize the database to run the tests
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest { // For some reason running all tests at once can cause some but that only the first pass, please try to run one at the time
	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void register_user_should_return_200_and_token_when_registered_user() {
		given()
				.body("""
						{
						    "name": "victor",
						    "login": "victor",
						    "password": "1234",
						    "role": "user"
						}
						""")
				.contentType("application/json")
				.when()
				.post("/auth/register")
				.then()
				.statusCode(200);
	}

	@Test
	public void login_should_return_200() {
		given()
				.body("""
                {
                    "name": "victor",
                    "login": "victor",
                    "password": "1234",
                    "role": "user"
                }
              """)
				.contentType("application/json")
				.when()
				.post("/auth/register")
				.then()
				.statusCode(200);

		given()
				.body("""
                {
                    "login": "victor",
                    "password": "1234"
                }
              """)
				.contentType("application/json")
				.when()
				.post("/auth/login")
				.then()
				.statusCode(200)
				.body("data", matchesPattern("^[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+$")); // Validate JWT format
	}

	@Test
	public void login_should_return_forbidden_when_registered_username_is_wrong() {
		// Step 1: Register the user
		given()
				.body("""
                {
                    "name": "victor",
                    "login": "victor",
                    "password": "1234",
                    "role": "user"
                }
              """)
				.contentType("application/json")
				.when()
				.post("/auth/register")
				.then()
				.statusCode(200);

		// Step 2: Login with the registered user
		given()
				.body("""
                {
                    "login": "victo",
                    "password": "1234"
                }
              """)
				.contentType("application/json")
				.when()
				.post("/auth/login")
				.then()
				.statusCode(403);
	}

	@Test
	public void login_should_return_forbidden_when_registered_user_password_is_wrong() {

		given()
				.body("""
                {
                    "name": "victor",
                    "login": "victor",
                    "password": "1234",
                    "role": "user"
                }
              """)
				.contentType("application/json")
				.when()
				.post("/auth/register")
				.then()
				.statusCode(200);

		given()
				.body("""
                {
                    "login": "victo",
                    "password": "124"
                }
              """)
				.contentType("application/json")
				.when()
				.post("/auth/login")
				.then()
				.statusCode(403);
	}

	@Test
	public void logout_should_return_200() {
		given()
				.body("""
						{
						    "name": "victor",
						    "login": "victor",
						    "password": "1234",
						    "role": "user"
						}
						""")
				.contentType("application/json")
				.when()
				.post("/auth/register")
				.then()
				.statusCode(200);

				String token = given()
				.body("""
						{
								"login": "victor",
								"password": "1234"
						}
					""")
				.contentType("application/json")
				.when()
				.post("/auth/login")
				.then()
				.statusCode(200)
				.body("data", matchesPattern("^[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+$"))
				.extract()
				.path("data");


		given()
				.header("Authorization", "Bearer " + token)
				.contentType("application/json")
				.when()
				.get("/auth/logout")
				.then()
				.statusCode(200);
	}

}
