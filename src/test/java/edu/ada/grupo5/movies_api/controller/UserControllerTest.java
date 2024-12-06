package edu.ada.grupo5.movies_api.controller;

import edu.ada.grupo5.movies_api.MoviesApiApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
/*
For this test to run correctly, you need to define the API token in the run configurations;
otherwise, Spring will not start, and the tests will be canceled.
 */
@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = MoviesApiApplication.class
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    private String token;

    @BeforeAll
    public void registerUser() {
        RestAssured.port = port;
        Map<String, String> registerData = new HashMap<>();
        registerData.put("login", "testUser");
        registerData.put("password", "testPassword");
        registerData.put("role", "admin");

        given()
                .contentType(ContentType.JSON)
                .body(registerData)
                .when()
                .post("/auth/register")
                .then()
                .statusCode(200);
    }

    @BeforeEach
    public void authenticateUser() {
        RestAssured.port = port;
        Map<String, String> loginData = new HashMap<>();
        loginData.put("login", "testUser");
        loginData.put("password", "testPassword");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginData)
                .when()
                .post("/auth/login");

        response.then().statusCode(200);

        token = response.jsonPath().getString("data");
    }

    @Test
    public void deve_retornar_um_user_pelo_login() {
        given()
                .header("Authorization", "Bearer " + token)
                .pathParam("login", "testUser")
        .when()
                .get("/user/get-user/{login}")
        .then()
                .statusCode(202)
                .contentType(ContentType.JSON)
                .body("login", equalTo("testUser"));
    }

    @Test
    public void deve_deletar_o_usuario_e_retornar_200(){
        given()
                .header("Authorization", "Bearer " + token)
                .pathParam("id", 1)
        .when()
                .delete("/user/{id}")
        .then()
                .statusCode(200);

    }
}
