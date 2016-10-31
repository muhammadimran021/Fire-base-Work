package com.example.imran.feragments.User_todo_pkg;

import android.widget.ImageButton;

/**
 * Created by Muhammad imran on 10/10/2016.
 */

public class Modules {
    private String Image;
    private String uid;
    private String name;
    private String city;
    private boolean check;


    public Modules() {
    }

    public Modules(String image, String uid, String name, String city, boolean check) {
        Image = image;
        this.uid = uid;
        this.name = name;
        this.city = city;
        this.check = check;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
