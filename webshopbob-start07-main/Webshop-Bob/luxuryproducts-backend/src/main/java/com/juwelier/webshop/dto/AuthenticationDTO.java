package com.juwelier.webshop.dto;

public class AuthenticationDTO {
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public AuthenticationDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
