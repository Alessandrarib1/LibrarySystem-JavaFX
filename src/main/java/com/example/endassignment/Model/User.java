package com.example.endassignment.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private int id;
    private LocalDate birthdate;

    public LocalDate getBirthdate() {
        return birthdate;
    }

    private static final long serialVersionUID = -4097136145996302687L;



    public User(String firstname, String lastname, String username, String password, LocalDate birthdate, TypeOfUser typeOfUser) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
    }

    public User(String firstname, String lastname, LocalDate birthdate, TypeOfUser typeOfUser) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }


    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }
}
