package ru.yandex.praktikum.userTest;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.User;
import ru.yandex.praktikum.generator.UserLoginGenerator;
import ru.yandex.praktikum.model.UserCreate;
import ru.yandex.praktikum.model.UserLogin;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;
import static ru.yandex.praktikum.generator.UserCreateGenerator.getRandomUser;

public class UserLoginTest {

    private UserCreate userCreate;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        user = new User();
        userCreate = getRandomUser();
        accessToken= user.create(userCreate).assertThat().statusCode(SC_OK).body("success", is(true)).extract().path("accessToken");
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
    @DisplayName("User authorization - авторизация пользователя")
    public void userAuthTest() {
        UserLogin userLogin = UserLoginGenerator.from(userCreate);
        user.login(userLogin).assertThat().statusCode(SC_OK).body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("User authorization with incorrect email- авторизация пользователя с неправильным email")
    public void userAuthWithIncorrectEmailTest() {
        UserLogin userLogin = UserLoginGenerator.incorrectEmail(userCreate);
        user.login(userLogin).assertThat().statusCode(SC_UNAUTHORIZED).body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("User authorization with incorrect password- авторизация пользователя с неправильным паролем")
    public void userAuthWithIncorrectPasswordTest() {
        UserLogin userLogin = UserLoginGenerator.incorrectPassword(userCreate);
        user.login(userLogin).assertThat().statusCode(SC_UNAUTHORIZED).body("message", equalTo("email or password are incorrect"));
    }
}
