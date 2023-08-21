package com.sixbert.authenticekuku;

public class ChatModel {


    String comment;
    String postTime;
    String userDp;
    String userId;
    String userName;
    String imageUrl;

    public ChatModel(){

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getUserDp() {
        return userDp;
    }

    public void setUserDp(String userDp) {
        this.userDp = userDp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ChatModel(String imageUrl, String comment, String postTime, String userDp, String userId, String userName) {
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.postTime = postTime;
        this.userDp = userDp;
        this.userId = userId;
        this.userName = userName;
    }
}
