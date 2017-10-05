package com.androidcurso.jefferson.whatsjeff.model;

/**
 * Created by jefferson on 05/10/17.
 */

public class Message {
    private String userFromId;
    private String UserToId;
    private String textMessage;

    public Message() {
    }

    public String getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(String userFromId) {
        this.userFromId = userFromId;
    }

    public String getUserToId() {
        return UserToId;
    }

    public void setUserToId(String userToId) {
        UserToId = userToId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
