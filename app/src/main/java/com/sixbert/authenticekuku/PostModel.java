package com.sixbert.authenticekuku;

public class PostModel {

    public PostModel(){

    }
    String phoneNumber, imageUrl, now, post, likeCounter,commentCounter;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getNow() {
        return now;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getLikeCounter() {
        return likeCounter;
    }

    public void setLikeCounter(String likeCounter) {
        this.likeCounter = likeCounter;
    }

    public String getCommentCounter() {
        return commentCounter;
    }

    public void setCommentCounter(String comntsCounter) {
        this.commentCounter = commentCounter;
    }




    public PostModel( String imageUrl, String post,
                     String now, String userId, String phoneNumber, String likeCounter, String commentCounter) {


        this.imageUrl = imageUrl ;
        this.now = now;
        this.post = post;
        this.phoneNumber = phoneNumber;
        this.likeCounter = likeCounter;
        this. commentCounter = commentCounter;

    }
}
