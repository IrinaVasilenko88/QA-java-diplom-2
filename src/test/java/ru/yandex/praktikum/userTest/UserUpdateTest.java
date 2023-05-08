package ru.yandex.praktikum.userTest;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.User;
import ru.yandex.praktikum.model.UserCreate;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static ru.yandex.praktikum.generator.UserCreateGenerator.getRandomUser;

public class UserUpdateTest {
    private UserCreate userCreate;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        user = new User();
        userCreate = getRandomUser();
        accessToken = user.create(userCreate).assertThat().statusCode(SC_OK).body("success", is(true)).extract().path("accessToken");
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
    @DisplayName("Authorized user changes email- Возможность изменения email у авторизованного пользователя")
    public void userAuthChangeEmailTest() {
        userCreate.setEmail(userCreate.getEmail() + "5");
        user.update(accessToken, userCreate).assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @Test
    @DisplayName("Authorized user changes password- Возможность изменения пароля у авторизованного пользователя")
    public void userAuthChangePasswordTest() {
        userCreate.setPassword(userCreate.getPassword() + "5");
        user.update(accessToken, userCreate).assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @Test
    @DisplayName("Authorized user changes name- Возможность изменения имени у авторизованного пользователя")
    public void userAuthChangeNameTest() {
        userCreate.setName(userCreate.getName() + "5");
        user.update(accessToken, userCreate).assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @Test
    @DisplayName("Unauthorized user cannot change email- Невозможность изменения email у неавторизованного пользователя")
    public void userUnAuthCanNotChangeEmailTest() {
        userCreate.setEmail(userCreate.getEmail() + "5");
        user.update("5", userCreate).assertThat().statusCode(SC_UNAUTHORIZED).body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Unauthorized user cannot change password- Невозможность изменения пароля у неавторизованного пользователя")
    public void userUnAuthCanNotChangePasswordTest() {
        userCreate.setPassword(userCreate.getPassword() + "5");
        user.update("5", userCreate).assertThat().statusCode(SC_UNAUTHORIZED).body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Unauthorized user cannot change name- Невозможность изменения имени у неавторизованного пользователя")
    public void userUnAuthCanNotChangeNameTest() {
        userCreate.setName(userCreate.getName() + "5");
        user.update("5", userCreate).assertThat().statusCode(SC_UNAUTHORIZED).body("message", equalTo("You should be authorised"));
    }
}
