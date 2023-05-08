package ru.yandex.praktikum.userTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.User;
import ru.yandex.praktikum.model.UserCreate;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static ru.yandex.praktikum.generator.UserCreateGenerator.*;


public class UserCreateTest {

    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        user = new User();

    }

    @After
    public void clearData() {
        if (accessToken != null) {
            user.delete(accessToken)
                    .assertThat()
                    .statusCode(SC_ACCEPTED)
                    .body("message", equalTo("User successfully removed"));
        }
    }

    @Test
    @DisplayName("Valid data user creation - Создание нового пользователя")
    public void userCanBeCreatedTest() {
        UserCreate userCreate = getRandomUser();
        ValidatableResponse response =user.create(userCreate);
        accessToken = response.extract().path("accessToken");
        response.assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @Test
    @DisplayName("Same data user creation - Создание дубликата пользователя")
    public void sameUserCanNotBeCreatedTest() {
        UserCreate userCreate = getRandomUser();
        ValidatableResponse response =user.create(userCreate);
        accessToken = response.extract().path("accessToken");
        response.assertThat().statusCode(SC_OK).body("success", is(true));
        getRandomUser();
        ValidatableResponse response2 =user.create(userCreate);
        response2.assertThat().statusCode(SC_FORBIDDEN).body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("User creation without name - Создание пользователя без имени")
    public void userWithoutNameCanNotBeCreatedTest() {
        UserCreate userCreate = getUserWithoutName();
        ValidatableResponse response =user.create(userCreate);
        accessToken = response.extract().path("accessToken");
        response.assertThat().statusCode(SC_FORBIDDEN).body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("User creation without email - Создание пользователя без email")
    public void userWithoutEmailCanNotBeCreatedTest() {
        UserCreate userCreate = getUserWithoutEmail();
        ValidatableResponse response =user.create(userCreate);
        accessToken = response.extract().path("accessToken");
        response.assertThat().statusCode(SC_FORBIDDEN).body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("User creation without password - Создание пользователя без пароля")
    public void userWithoutPasswordCanNotBeCreatedTest() {
        UserCreate userCreate = getUserWithoutPassword();
        ValidatableResponse response =user.create(userCreate);
        accessToken = response.extract().path("accessToken");
        response.assertThat().statusCode(SC_FORBIDDEN).body("message", equalTo("Email, password and name are required fields"));
    }
}
