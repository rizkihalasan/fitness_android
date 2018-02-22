package com.example.leo.fitnessdiy.model;

import android.content.SharedPreferences;

public class UsersSharedPreferences {
    public static final String ID_USERS = "id_users";
    public static final String USERNAME_USERS = "username_users";
    public static final String PASSWORD_USERS = "password_users";
    public static final String EMAIL_USERS = "email_users";
    public static final String PHONE_NUMBER_USERS = "phone_number_users";
    public static final String AGE_USERS = "age_users";
    public static final String ADDRESS_USERS = "address_users";
    public static final String LEVEL_USERS = "level_users";

    public static void setUserSharedPreferences(SharedPreferences mPreferences, Users user){
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(ID_USERS, user.getId());
        preferencesEditor.putString(USERNAME_USERS, user.getUsername());
        preferencesEditor.putString(PASSWORD_USERS, user.getPassword());
        preferencesEditor.putString(EMAIL_USERS, user.getEmail());
        preferencesEditor.putString(PHONE_NUMBER_USERS, user.getPhone_number());
        preferencesEditor.putInt(AGE_USERS, user.getAge());
        preferencesEditor.putString(ADDRESS_USERS, user.getAddress());
        preferencesEditor.putString(LEVEL_USERS, user.getLevel());
        preferencesEditor.apply();
    }

    public static void setDefaultSharedPreferences(SharedPreferences mPreferences){
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(ID_USERS, 0);
        preferencesEditor.putString(USERNAME_USERS, "");
        preferencesEditor.putString(PASSWORD_USERS, "");
        preferencesEditor.putString(EMAIL_USERS, "");
        preferencesEditor.putString(PHONE_NUMBER_USERS, "");
        preferencesEditor.putInt(AGE_USERS, 0);
        preferencesEditor.putString(ADDRESS_USERS, "");
        preferencesEditor.putString(LEVEL_USERS, "");
        preferencesEditor.apply();
    }
}
