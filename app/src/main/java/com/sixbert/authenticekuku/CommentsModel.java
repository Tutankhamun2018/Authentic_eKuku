package com.sixbert.authenticekuku;


public class CommentsModel {

    String cId;

    String actualComment;

    String commentTime;


    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getActualComment() {
        return actualComment;
    }

    public void setActualComment(String actualComment) {
        this.actualComment = actualComment;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public CommentsModel() {
    }

    String uid;

    public CommentsModel(String cId, String actualComment, String commentTime,  String uid) {
        this.cId = cId;
        this.actualComment = actualComment;
        this.commentTime = commentTime;
        this.uid = uid;

    }


}
