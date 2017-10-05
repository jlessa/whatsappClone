package com.androidcurso.jefferson.whatsjeff.model;

/**
 * Created by jefferson on 05/10/17.
 */

public class Chat {
    private String userToId;
    private String name;
    private String textMessage;

    public Chat() {
    }

    public String getUserToId() {
        return userToId;
    }

    public void setUserToId(String userToId) {
        this.userToId = userToId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
