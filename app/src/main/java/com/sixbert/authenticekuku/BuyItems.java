package com.sixbert.authenticekuku;

import java.util.Comparator;

public class BuyItems {
    private String typeOfItem;
    private String phoneNumber;
    private Integer numberOfProduct;
    private Integer priceOfProduct;

    public static final Comparator<BuyItems> typeComparator = new Comparator<BuyItems>() {
        @Override
        public int compare(BuyItems t1, BuyItems t2) {
            return t1.getTypeOfItem().compareTo(t2.getTypeOfItem());
        }
    };

    public static final Comparator<BuyItems> qtyComparator = new Comparator<BuyItems>() {
        @Override
        public int compare(BuyItems t1, BuyItems t2) {
            return t1.getNumberOfProduct() - (t2.getNumberOfProduct());
        }
    };

    public static final Comparator<BuyItems> priceComparator = new Comparator<BuyItems>() {
        @Override
        public int compare(BuyItems t1, BuyItems t2) {
            return (int) t1.getPriceOfProduct() - (t2.getPriceOfProduct());
        }
    };



    public BuyItems(){
        //empty constructor needed by Firebase
    }
    public BuyItems (String typeOfItem, String phoneNumber, Integer numberOfProduct,
                     Integer priceOfProduct){
        this.typeOfItem = typeOfItem;
        this.phoneNumber = phoneNumber;
        this.numberOfProduct = numberOfProduct;
        this.priceOfProduct = priceOfProduct;

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

