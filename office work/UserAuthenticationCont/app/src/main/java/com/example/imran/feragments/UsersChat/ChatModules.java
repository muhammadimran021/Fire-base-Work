package com.example.imran.feragments.UsersChat;

/**
 * Created by Muhammad imran on 10/31/2016.
 */

public class ChatModules {
private String UUID;
    private String Text;
    private String PushId;

    public ChatModules(String UUID, String text, String pushId) {
        this.UUID = UUID;
        Text = text;
        PushId = pushId;
    }

    public ChatModules() {
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getPushId() {
        return PushId;
    }

    public void setPushId(String pushId) {
        PushId = pushId;
    }
}
