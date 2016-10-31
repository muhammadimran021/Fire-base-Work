package com.example.imran.feragments.UsersChat;

/**
 * Created by Muhammad imran on 10/31/2016.
 */

public class PushKey {
    private String currentId;
    private String message;
    private String pushId;

    public PushKey(String currentId, String message, String pushId) {
        this.currentId = currentId;
        this.message = message;
        this.pushId = pushId;
    }

    public PushKey() {
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
