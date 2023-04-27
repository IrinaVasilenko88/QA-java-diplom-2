package ru.yandex.praktikum.orderTest;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.ClientOrderSteps;
import ru.yandex.praktikum.client.User;
import ru.yandex.praktikum.model.OrderCreate;
import ru.yandex.praktikum.model.UserCreate;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static ru.yandex.praktikum.generator.UserCreateGenerator.getRandomUser;

public class OrderCreateTest {

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
    @DisplayName("Create order by authorized user - создание заказа авторизованным пользователем")
    public void createOrderByAuthUserTest() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa77", "61c0c5a71d1f82001bdaaa7a");
        OrderCreate orderCreate = new OrderCreate(ingredients);
        ClientOrderSteps clientOrderSteps = new ClientOrderSteps();
        clientOrderSteps.createOrder(accessToken, orderCreate).assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @Test
    @DisplayName("Create order by non-authorized user - создание заказа неавторизованным пользователем")
    public void createOrderByNonAuthUserTest() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa77", "61c0c5a71d1f82001bdaaa7a");
        OrderCreate orderCreate = new OrderCreate(ingredients);
        ClientOrderSteps clientOrderSteps = new ClientOrderSteps();
        clientOrderSteps.createOrder("0", orderCreate).assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @Test
    @DisplayName("Order cannot be created without ingredients - невозможность создания заказа без ингредиентов")
    public void orderCanNotBeCreatedWithoutIngredientsTest() {
        OrderCreate orderCreate = new OrderCreate();
        ClientOrderSteps clientOrderSteps = new ClientOrderSteps();
        clientOrderSteps.createOrder(accessToken, orderCreate).assertThat().statusCode(SC_BAD_REQUEST).body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Order cannot be created with incorrect ingredients hash - невозможность создания заказа c неверным хешем ингредиентов")
    public void orderCanNotBeCreatedWithIncorrectIngredientsTest() {
        List<String> ingredients = List.of("60d3463f7034a000269f45e", "60d3463f7034a000269f45e", "60d3463f7034a000269f45e", "60d3463f7034a000269f45e");
        OrderCreate orderCreate = new OrderCreate(ingredients);
        ClientOrderSteps clientOrderSteps = new ClientOrderSteps();
        clientOrderSteps.createOrder(accessToken, orderCreate).assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
