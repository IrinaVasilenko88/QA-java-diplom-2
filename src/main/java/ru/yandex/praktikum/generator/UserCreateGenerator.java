package ru.yandex.praktikum.generator;

import ru.yandex.praktikum.model.UserCreate;
import org.apache.commons.lang3.RandomStringUtils;

public class UserCreateGenerator {
    public static UserCreate getRandomUser() {
        UserCreate userCreate = new UserCreate();
        userCreate.setEmail(RandomStringUtils.randomAlphabetic(7)+"@yandex.ru");
        userCreate.setPassword("12345II!!!");
        userCreate.setName(RandomStringUtils.randomAlphabetic(7)+ "Irina");
        return userCreate;
    }

    public static UserCreate getUserWithoutPassword() {
        UserCreate userCreate = new UserCreate();
        userCreate.setEmail(RandomStringUtils.randomAlphabetic(7)+"@yandex.ru");
        userCreate.setName(RandomStringUtils.randomAlphabetic(7));
        return userCreate;
    }

    public static UserCreate getUserWithoutEmail() {
        UserCreate userCreate = new UserCreate();
        userCreate.setPassword(RandomStringUtils.randomAlphabetic(7) +"!!!");
        userCreate.setName(RandomStringUtils.randomAlphabetic(7));
        return userCreate;
    }

    public static UserCreate getUserWithoutName() {
        UserCreate userCreate = new UserCreate();
        userCreate.setPassword(RandomStringUtils.randomAlphabetic(7) +"!!!");
        userCreate.setName(RandomStringUtils.randomAlphabetic(7));
        return userCreate;
    }
}
