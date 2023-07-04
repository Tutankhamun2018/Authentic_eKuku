package com.sixbert.authenticekuku;

import android.widget.TextView;

public class ItemShowMain {

    private String itemName;
    private String usersCount;
    private int itemImage;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(String usersCount) {
        this.usersCount = usersCount;
    }

    public int getItemImage() {
        return itemImage;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public ItemShowMain(String itemName, String usersCount, int itemImage) {
        this.itemName = itemName;
        this.usersCount = usersCount;
        this.itemImage = itemImage;
    }
}
