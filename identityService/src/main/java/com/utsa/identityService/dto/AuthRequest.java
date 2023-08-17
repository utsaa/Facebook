package com.utsa.identityService.dto;

import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
public class AuthRequest {

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthRequest that)) return false;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword());
    }

    public AuthRequest(String username, String password) {
        this.name = username;
        this.password = password;
    }

    public AuthRequest() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword());
    }

    private String password;

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "username='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
