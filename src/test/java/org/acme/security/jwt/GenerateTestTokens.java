package org.acme.security.jwt;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;

import java.util.Arrays;
import java.util.HashSet;

import static java.util.Arrays.asList;

public class GenerateTestTokens {

    public static String createUserAdminToken() {
        return Jwt.issuer("https://example.com/issuer")
                .upn("admin@intern.io")
                .groups(new HashSet<>(asList("User", "Admin")))
                .claim(Claims.birthdate.name(), "1980-05-02")
                .sign();
    }

    public static String createAdvisorToken() {
        return Jwt.issuer("https://example.com/issuer")
                .upn("advisor@extern.io")
                .groups(new HashSet<>(asList("Advisor")))
                .claim(Claims.birthdate.name(), "2001-07-13")
                .sign();
    }

    public static void main(String[] args) {
        System.out.println("Test tokens. They will expire after 5 minutes from now.");
        String userAdminToken = createUserAdminToken();
        System.out.println("Token of a person with roles User and Admin\n" + userAdminToken);

        System.out.println();

        String advisorToken = createAdvisorToken();
        System.out.println("Token of a person with role Advisor\n" + advisorToken);

        System.exit(0);
    }
}
