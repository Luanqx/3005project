package com.company;

import java.util.ArrayList;

public class Order {
    Integer order_number;
    String shipping_address;
    String billing_address;
    Integer user_id;
    ArrayList<String> ISBNs= new ArrayList<String>();
    String Status="In ware house";

    public Order(String shipping_address, String billing_address,ArrayList<String> ISBNs,Integer user_id, Integer order_number) {
       this.billing_address=billing_address;
       this.shipping_address=shipping_address;
       this.ISBNs=ISBNs;
       this.user_id=user_id;
       this.order_number=order_number;


    }
    public void print() {
        System.out.println("User's account: "+user_id);
        System.out.println("shipping_address: "+shipping_address);
        System.out.println("billing_address: "+billing_address);
        System.out.println("Purchased books: "+ISBNs);
        System.out.println("Status: "+Status);
    }

}
