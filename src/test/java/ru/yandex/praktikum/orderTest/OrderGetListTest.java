package ru.yandex.praktikum.orderTest;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.ClientOrderSteps;
import ru.yandex.praktikum.client.User;
import ru.yandex.praktikum.model.UserCreate;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static ru.yandex.praktikum.generator.UserCreateGenerator.getRandomUser;

public class OrderGetListTest {

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
            user.delete(accessToken).assertThat().statusCode(SC_ACCEPTED).body("message", equalTo("User successfully removed"));
        }
    }

    @Test
    @DisplayName("Get list of orders of authorized user - Получение списка заказов авторизованного пользователя")
    public void getOrderListOfAuthUserTest() {
        ClientOrderSteps clientOrderSteps = new ClientOrderSteps();
        clientOrderSteps.getOrdersList(accessToken).assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @Test
    @DisplayName("List of orders of non-authorized user cannot be gotten- Невозможность получения списка заказов неавторизованного пользователя")
    public void orderListOfNonAuthUserCaNotBeGottenTest() {
        ClientOrderSteps clientOrderSteps = new ClientOrderSteps();
        clientOrderSteps.getOrdersList("0").assertThat().statusCode(SC_UNAUTHORIZED).body("message", equalTo("You should be authorised"));
    }
}
