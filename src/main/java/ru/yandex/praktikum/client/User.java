package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.config.Rest;
import ru.yandex.praktikum.model.UserCreate;
import ru.yandex.praktikum.model.UserLogin;

import static io.restassured.RestAssured.given;

public class User extends Rest {

    @Step("Send POST Request to /auth/register - create User. Создание пользователя")
    public ValidatableResponse create(UserCreate userCreate) {
        return given()
                .spec(getBaseRequestSpec())
                .body(userCreate)
                .post("/auth/register")
                .then();
    }

    @Step("Send POST Request to /auth/login - Login as User. Логин под пользователем")
    public ValidatableResponse login(UserLogin userLogin) {
        return given()
                .spec(getBaseRequestSpec())
                .body(userLogin)
                .when()
                .post("/auth/login")
                .then();
    }

    @Step("Send DELETE Request to /auth/user - Delete User. Удаление пользователя")
    public ValidatableResponse delete(String token) {
        return given()
                .spec(getBaseRequestSpec())
                .header("Authorization", token)
                .when()
                .delete("/auth/user")
                .then();
    }

    @Step("Send PATCH Request to /auth/user - Update User Data. Изменение данных пользователем")
    public ValidatableResponse update(String token, UserCreate userCreate) {
        return given()
                .spec(getBaseRequestSpec())
                .header("Authorization", token)
                .when()
                .body(userCreate)
                .patch("/auth/user")
                .then();
    }
}
