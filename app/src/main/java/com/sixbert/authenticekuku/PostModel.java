package com.sixbert.authenticekuku;

public class PostModel {

    public PostModel(){

    }
    String phoneNumber, imageUrl, now, post, likeCounter,commentCounter, profileOrCoverPhoto,
            like, name, uid, pid, udp;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getProfileOrCoverPhoto() {
        return profileOrCoverPhoto;
    }

    public void setProfileOrCoverPhoto(String profileOrCoverPhoto) {
        this.profileOrCoverPhoto = profileOrCoverPhoto;
    }

    public String getUdp(){
        return udp;
    }
    public void SetUdp(String udp){this.udp = udp;}

    public String getLike(){
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPid(){return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public  String getName(){return name;}

    public  String setName(String name){ this.name = name;
        return name;

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
                     String now, String name, String phoneNumber, String likeCounter,
                      String commentCounter, String profileOrCoverPhoto, String like,
                      String uid, String pid, String udp) {


        this.imageUrl = imageUrl ;
        this.now = now;
        this.profileOrCoverPhoto = profileOrCoverPhoto;
        this.post = post;
        this.name = name;
        this.uid = uid;
        this.pid = pid;
        this.udp = udp;
        this.phoneNumber = phoneNumber;
        this.likeCounter = likeCounter;
        this. commentCounter = commentCounter;
        this.like = like;

    }
}

