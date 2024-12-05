package edu.ada.grupo5.movies_api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtTestUtils {
    public static String generateMockToken() {
        return JWT.create()
                .withIssuer("movies-api")
                .withClaim("scope", "read")
                .sign(Algorithm.HMAC256("secret"));
    }
}