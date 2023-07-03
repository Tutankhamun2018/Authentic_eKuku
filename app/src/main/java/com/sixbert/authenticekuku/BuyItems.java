package com.sixbert.authenticekuku;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Comparator;
import java.util.Date;

public class BuyItems {
    private String townOfSeller;
    private String wardOfSeller;
    private String streetOfSeller;
    private String typeOfItem;
    private String phoneNumber;
    private Integer numberOfProduct;
    private Integer priceOfProduct;

    private String extraDescription;
    @ServerTimestamp
    private Date today;

   public BuyItems(){
        //empty constructor needed by Firebase
    }



    public BuyItems (String townOfSeller,String wardOfSeller, String streetOfSeller, String typeOfItem, String phoneNumber, int numberOfProduct,
                     int priceOfProduct,String extraDescription, Date date){
       this.townOfSeller = townOfSeller;
        this.wardOfSeller = wardOfSeller;
        this.streetOfSeller = streetOfSeller;
        this.typeOfItem = typeOfItem;
        this.phoneNumber = phoneNumber;
        this.numberOfProduct = numberOfProduct;
        this.priceOfProduct = priceOfProduct;
        this.extraDescription = extraDescription;
        this.today = date;

    }

    public BuyItems(int parseInt) {
    }


    public Date getDate(){
       return today;
    }

    public void setDate(Date date){
       this.today = date;
    }

    public String getTownOfSeller() {
        return townOfSeller;
    }
    public String getWardOfSeller() {
        return wardOfSeller;
    }

    public String getStreetOfSeller() {
        return streetOfSeller;
    }

    public void setTownOfSeller(String townOfSeller) {
        this.townOfSeller = townOfSeller;
    }

    public void setWardOfSeller(String wardOfSeller) {
        this.wardOfSeller = wardOfSeller;
    }

    public void setstreetOfSeller(String streetOfSeller) {
        this.streetOfSeller = streetOfSeller;
    }
    public String getTypeOfItem() {
        return typeOfItem;
    }

    public void setTypeOfItem(String typeOfItem) {
        this.typeOfItem = typeOfItem;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getExtraDescription() {return extraDescription;}

    public void setExtraDescription(String extraDescription) {
       this.extraDescription = extraDescription;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getNumberOfProduct() {
        return numberOfProduct;
    }
    //customised setter to overcome deserialize object error
    //java.lang.RuntimeException: Could not deserialize object. Failed to convert a value of type java.lang.String to int
    public void setNumberOfProduct(Object numberOfProduct) {
        try {
            this.numberOfProduct = Integer.parseInt(numberOfProduct.toString());
        } catch (Exception e) {
            this.numberOfProduct = 0;
        }
    }


    public int getPriceOfProduct() {
        return priceOfProduct;
    }

    //customised setter to overcome deserialize object error
    //java.lang.RuntimeException: Could not deserialize object. Failed to convert a value of type java.lang.String to double
    public void setPriceOfProduct(Object priceOfProduct) {
        try {
            this.priceOfProduct = Integer.parseInt(priceOfProduct.toString());
        } catch (Exception e) {
            this.priceOfProduct = 0;
        }
    }
}

