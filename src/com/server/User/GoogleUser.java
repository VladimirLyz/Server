package com.server.User;

public class GoogleUser extends User {
    private final String token;

    public Integer getHashToken() {
        return hashToken;
    }

    private final Integer hashToken;

    public String getToken() {
        return token;
    }

    public GoogleUser(String token)
    {
        this.token = token;
        this.hashToken = token.hashCode();
    }
}
