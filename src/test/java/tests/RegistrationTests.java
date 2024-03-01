package tests;

import models.RegistrationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import models.*;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RegistrationActionSpec.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static specs.RegistrationActionSpec.requestPostPutSpec;

public class RegistrationTests extends TestBase {
    @DisplayName("Проверка создания пользователя")
    @Test
    void checkRegistrationSuccessfulTest() {
        RegistrationRequest registrationData = new RegistrationRequest();
        registrationData.setEmail("eve.holt@reqres.in");
        registrationData.setPassword("pistol");


        CreateAndUpdateRegistrationResponse response = step("Отправляем запрос на создание пользователя",
                () -> given(requestPostPutSpec)
                        .body(registrationData)
                        .contentType(JSON)
                        .when()
                        .post("/register")
                        .then()
                        .spec(response200)
                        .extract().as(CreateAndUpdateRegistrationResponse.class)
        );

        step("Проверяем, что пользователь создан с правильными параметрами", () -> {
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @Test
    void checkRegistrationEmptyEmailTest() {
        RegistrationRequest registrationData = new RegistrationRequest();
        registrationData.setEmail("");
        registrationData.setPassword("pistol");

        CreateAndUpdateRegistrationResponse response = step("Отправляем запрос на создание пользователя",
            () -> given(requestPostPutSpec)
                .body(registrationData)
                .contentType(JSON)
            .when()
                .post("/register")
            .then()
                .spec(response400)
                .extract().as(CreateAndUpdateRegistrationResponse.class)
        );

        step("Проверяем, что пользователь создан с правильными параметрами", () -> {
            assertEquals("Missing email or username", response.getError());
        });
    }

    @Test
    void checkRegistrationEmptyPasswordTest() {
        RegistrationRequest registrationData = new RegistrationRequest();
        registrationData.setEmail("eve.holt@reqres.in");
        registrationData.setPassword("");

        CreateAndUpdateRegistrationResponse response = step("Отправляем запрос на создание пользователя",
            () -> given(requestPostPutSpec)
                .body(registrationData)
                .contentType(JSON)
            .when()
                .post("/register")
            .then()
                .spec(response400)
                .extract().as(CreateAndUpdateRegistrationResponse.class)
        );

        step("Проверяем, что пользователь создан с правильными параметрами", () -> {
            assertEquals("Missing password", response.getError());
        });
    }

    @Test
    void checkRegistrationEmptyEmailAndPasswordTest() {
        RegistrationRequest registrationData = new RegistrationRequest();
        registrationData.setEmail("");
        registrationData.setPassword("");

        CreateAndUpdateRegistrationResponse response = step("Отправляем запрос на создание пользователя",
            () -> given(requestPostPutSpec)
                .body(registrationData)
                .contentType(JSON)
            .when()
                .post("/register")
            .then()
                .spec(response400)
                .extract().as(CreateAndUpdateRegistrationResponse.class)
        );

        step("Проверяем, что пользователь создан с правильными параметрами", () -> {
            assertEquals("Missing email or username", response.getError());
        });
    }
}
