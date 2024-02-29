package com.sixbert.authenticekuku;

public class MultiSubs {

    String planName, planPrice, planStatus;
    int planIndex;

    public  MultiSubs (String planName, String planPrice, String planStatus, int planIndex){
        this.planName = planName;
        this.planPrice = planPrice;
        this.planStatus = planStatus;
        this.planIndex = planIndex;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(String planPrice) {
        this.planPrice = planPrice;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public int getPplanIndex() {
        return planIndex;
    }

    public void setPplanIndex(int pplanIndex) {
        this.planIndex = pplanIndex;
    }
}
