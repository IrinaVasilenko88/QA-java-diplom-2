package ru.yandex.praktikum.generator;

import ru.yandex.praktikum.model.UserCreate;
import ru.yandex.praktikum.model.UserLogin;

public class UserLoginGenerator {

    public static UserLogin from(UserCreate userCreate) {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(userCreate.getEmail());
        userLogin.setPassword(userCreate.getPassword());
        return userLogin;
    }

    public static UserLogin incorrectEmail(UserCreate userCreate) {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(userCreate.getEmail() +"5");
        userLogin.setPassword(userCreate.getPassword());
        return userLogin;
    }

    public static UserLogin incorrectPassword(UserCreate userCreate) {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(userCreate.getEmail());
        userLogin.setPassword(userCreate.getPassword()+"5");
        return userLogin;
    }

    public static UserLogin changeEmail(UserCreate userCreate) {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(userCreate.getEmail());
        return userLogin;
    }
}
