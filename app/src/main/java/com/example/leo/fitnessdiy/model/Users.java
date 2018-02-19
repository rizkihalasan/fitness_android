package com.example.leo.fitnessdiy.model;

public class Users {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone_number;
    private int age;
    private String address;
    private String level;

    public Users(int id, String username, String password, String email, String phone_number, int age, String address, String level) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
        this.age = age;
        this.address = address;
        this.level = level;
    }

    public Users() {
        this.id = 0;
        this.username = "";
        this.password = "";
        this.email = "";
        this.phone_number = "";
        this.age = 0;
        this.address = "";
        this.level = "";
    }

    public int getId() {
        return id;
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

    public String getPhone_number() {
        return phone_number;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getLevel() {
        return level;
    }
}
