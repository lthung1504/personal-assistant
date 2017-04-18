package com.example.thanhhungle.personalassistant.model;

/**
 * Created by thanhhung.le on 18/4/17.
 */

public class ApiUser {
    public long id;
    public String firstname;
    public String lastname;

    @Override
    public String toString() {
        return "ApiUser{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}