package com.example.kahoot.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenRequest {

    private String refreshToken;


    public TokenRequest() {
    }

    public TokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
