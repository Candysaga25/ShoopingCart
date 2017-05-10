package com.ecommerceapp.model;

/**
 * Created by Sagar Shedge on 6/12/16.
 */

public class RemoveProduct {
    public int position;
    public  Product product;
    public RemoveProduct(int position, Product product){
     this.position = position;
     this.product = product;
    }
}
