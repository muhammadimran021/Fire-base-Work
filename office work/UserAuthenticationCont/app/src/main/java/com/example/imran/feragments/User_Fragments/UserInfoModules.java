package com.example.imran.feragments.User_Fragments;

/**
 * Created by Muhammad imran on 10/20/2016.
 */

public class UserInfoModules {

    private String UUID;
    private String userImage;
    private String firstname;
    private String lastname;
    private String userEmail;
    private String userPassword;

    public UserInfoModules(String UUID, String userImage, String firstname, String lastname, String userEmail, String userPassword) {
        this.UUID = UUID;
        this.userImage = userImage;
        this.firstname = firstname;
        this.lastname = lastname;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public UserInfoModules() {
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
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