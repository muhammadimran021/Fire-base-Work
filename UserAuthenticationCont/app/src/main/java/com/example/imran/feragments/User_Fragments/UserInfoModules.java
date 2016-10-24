package com.example.imran.feragments.User_Fragments;

/**
 * Created by Muhammad imran on 10/20/2016.
 */

public class UserInfoModules {

    private String firstname;
    private String lastname;
    private String userEmail;
    private String userPassword;

    public UserInfoModules(String firstname, String lastname, String userEmail, String userPassword) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public UserInfoModules() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}