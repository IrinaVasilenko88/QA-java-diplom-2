package ru.yandex.praktikum.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static ru.yandex.praktikum.config.Config.BASE_URI;

public class Rest {
    public RequestSpecification getBaseRequestSpec() {
        return new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri(BASE_URI).build();
    }
}
