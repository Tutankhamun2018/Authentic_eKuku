package com.sixbert.authenticekuku;

public class Sell {

    private String qtyOfProduct, prOfProduct;

    public Sell(){
        //empty constructor needed by Firebase
    }
    public Sell (String qtyOfProduct,
                     String prOfProduct){

        this.qtyOfProduct = qtyOfProduct;
        this.prOfProduct = prOfProduct;

    }
    public String getQtyOfProduct() {
        return qtyOfProduct;
    }

    public void setQtyOfProduct(String qtyOfProduct) {
        this.qtyOfProduct = qtyOfProduct;
    }

    public String getPrOfProduct() {
        return prOfProduct;
    }

    public void setPrOfProduct(String prOfProduct) {
        this.prOfProduct = prOfProduct;
    }




}

