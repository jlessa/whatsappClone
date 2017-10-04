package com.androidcurso.jefferson.whatsjeff.model;

import com.androidcurso.jefferson.whatsjeff.config.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jefferson on 29/09/17.
 */

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private List<User> contacts;

    public User() {
        this.contacts = new ArrayList<>();
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contacts = new ArrayList<>();
    }

    public void saveUser() {
        DatabaseReference firebase = FirebaseConfig.getFirebaseInstance();
        firebase.child("users").child(getId()).setValue(this);
    }


    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void addContacts(User user) {
        this.contacts.add(user);
    }
}
