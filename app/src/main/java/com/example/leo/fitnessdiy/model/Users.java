package com.example.leo.fitnessdiy.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static Users initializeData(String data) {
        Users user = new Users();
        try {
//            JSONArray parser = new JSONArray(data);
            JSONObject json = new JSONObject(data);
//                    parser.getJSONObject(0);

            int id = Integer.parseInt(json.getString("id"));
            String username = json.getString("username");
            String password = json.getString("password");
            String email = json.getString("email");
            String phone_number = json.getString("phone_number");
            int age = Integer.parseInt(json.getString("age"));
            String address = json.getString("address");
            String level = json.getString("level");
            user = new Users(id, username, password, email, phone_number, age, address, level);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
