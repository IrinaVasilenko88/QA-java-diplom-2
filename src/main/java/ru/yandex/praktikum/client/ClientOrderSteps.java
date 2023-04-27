package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.config.Rest;
import ru.yandex.praktikum.model.OrderCreate;

import static io.restassured.RestAssured.given;

public class ClientOrderSteps extends Rest {

    @Step("Send POST Request to /orders -Create order. Создание заказа")
    public ValidatableResponse createOrder(String token, OrderCreate orderCreate) {
        return given()
                .spec(getBaseRequestSpec())
                .header("Authorization", token)
                .body(orderCreate)
                .post("/orders")
                .then();
    }


    @Step("Send GET Request to /orders -GET orders list. Получение списка заказов")
    public ValidatableResponse getOrdersList(String token) {
        return given()
                .header("Authorization", token)
                .spec(getBaseRequestSpec())
                .when()
                .get("/orders")
                .then();
    }
}
