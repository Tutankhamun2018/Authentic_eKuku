package com.sixbert.authenticekuku;


public class CommentsModel {

    String cId;
    String uname;
    String comment;

    String udp;

    String now;

    public String getUdp() {
        return udp;
    }


    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }



    public CommentsModel() {
    }



    public CommentsModel(String cId, String comment, String now,  String uname, String udp) {
        this.cId = cId;
        this.comment = comment;
        this.now = now;
        this.uname = uname;
        this.udp = udp;

    }


}
