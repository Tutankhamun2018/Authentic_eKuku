package com.sixbert.authenticekuku;

import java.util.Date;
import java.util.HashMap;


public class InAppChat {
    private String messageUser;
    private String messageText;
    private String messageUserID;
    private long timeStamp;
    private long likesCounter;
    private final HashMap<String, Boolean> messageLikes = new HashMap<>();


    public InAppChat(String messageUser, String messageText, String messageUserID, long timeStamp, long likesCounter) {
        this.messageUser = messageUser;
        this.messageText = messageText;
        this.messageUserID = messageUserID;
        this.timeStamp = new Date().getTime();
        this.likesCounter = likesCounter;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUserID() {
        return messageUserID;
    }

    public void setMessageUserID(String messageUserID) {
        this.messageUserID = messageUserID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(long likesCounter) {
        this.likesCounter = likesCounter;
    }

}
