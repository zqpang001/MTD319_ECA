package com.example.mtd319_tma02;

public class Credential {
    public String username;
    public String password;
    public String email;
    public String mobile;

    public Credential(String username, String password, String email, String mobile) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String toString() {
        return "username : " + username + "\nPassword : " + password + "\nEmail : " + email;
    }
}
