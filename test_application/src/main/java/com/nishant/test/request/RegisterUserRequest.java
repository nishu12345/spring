package com.nishant.test.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class RegisterUserRequest implements Serializable {
    private static final long serialVersionUID = -8629037203093776844L;

    private Long id;

    private String username;

    private String email;

    private String password;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RegisterUserRequest [");
        sb.append("id=").append(id);
        sb.append(", username= ").append(username);
        sb.append(", email= ").append(email);
        sb.append(", password= ").append(password);
        sb.append("]");
        return sb.toString();
    }
}
