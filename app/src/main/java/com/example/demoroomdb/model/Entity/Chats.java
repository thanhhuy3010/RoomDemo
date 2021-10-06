package com.example.demoroomdb.model.Entity;

public class Chats {
    String message, receiver, sender;
    private static Chats chats;
    public Chats getInstance (String sender, String receiver, String message) {
        if (chats == null) {
            chats = new Chats("","","");
        }
        return chats;
    }

    public Chats() {}

    public Chats(String sender, String receiver, String message) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
