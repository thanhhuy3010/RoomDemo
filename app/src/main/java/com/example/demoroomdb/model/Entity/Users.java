package com.example.demoroomdb.model.Entity;

public class Users {
    String id, username, email;

    public Users(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Users() {}

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
