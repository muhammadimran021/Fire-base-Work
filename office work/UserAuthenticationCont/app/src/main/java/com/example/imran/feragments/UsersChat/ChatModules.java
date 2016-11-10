package com.example.imran.feragments.UsersChat;

/**
 * Created by Muhammad imran on 10/31/2016.
 */

public class ChatModules {
    private String conversationId;
    private String userId;

    public ChatModules(String conversationId, String userId) {
        this.conversationId = conversationId;
        this.userId = userId;

    }

    public ChatModules() {
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
