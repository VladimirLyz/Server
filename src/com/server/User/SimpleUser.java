package com.server.User;

public class SimpleUser extends User {
    String login, password;

    public int getHashLogin() {
        return hashLogin;
    }

    public String getLogin() {
        return login;
    }

    int hashLogin, hashPassword;
    public SimpleUser(String login, String password) {
        this.login = login;
        this.password = password;
        hashLogin = login.hashCode();
        hashPassword = password.hashCode();
    }
}
