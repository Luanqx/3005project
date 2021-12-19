package com.company;

import java.util.ArrayList;

public class report {
    ArrayList<String> Gernre= new ArrayList<String>();
    ArrayList<Integer> Gernre_sales= new ArrayList<Integer>();
    ArrayList<String> Author= new ArrayList<String>();
    ArrayList<Integer> Author_sales= new ArrayList<Integer>();
    double expenditure = 0.0;
    Integer sales= 0;

    public void add_gernre_report(String gernre) {
        if(Gernre.isEmpty()){
            Gernre.add(gernre);
            Gernre_sales.add(1);
        }
        else if(Gernre.contains(gernre)) {
            for (int i = 0; i < Gernre.size(); i++) {
                if (Gernre.get(i).equals(gernre)) {
                    Gernre_sales.set(i,Gernre_sales.get(i)+1);
                }
            }
        }
        else{
            Gernre.add(gernre);
            Gernre_sales.add(1);
        }

    }
    public void add_author_report(String author) {
        if(Author.isEmpty()){
            Author.add(author);
            Author_sales.add(1);
        }
        else if(Author.contains(author)) {
            for (int i = 0; i < Gernre.size(); i++) {
                if (Author.get(i).equals(author)) {
                    Author_sales.set(i,Author_sales.get(i)+1);
                }
            }
        }
        else{
            Author.add(author);
            Author_sales.add(1);
        }

    }
    public void add_expenditure_report(Integer price,Integer percentage) {
        this.sales=this.sales+price;
        this.expenditure=this.expenditure+price*(percentage/100.0);


    }

    public void print(){
        System.out.println("***********************");
        System.out.println("Sales for each gernre");
        for (int i=0;i<this.Gernre.size();i++){
            System.out.println(Gernre.get(i)+" : "+Gernre_sales.get(i));
        }
        System.out.println("***********************");
        System.out.println("Sales for each author");
        for (int i=0;i<this.Author.size();i++){
            System.out.println(Author.get(i)+" : "+Author_sales.get(i));
        }
        System.out.println("***********************");
        System.out.println("Total Income: "+sales);
        System.out.println("Total expenditure: "+expenditure);
        System.out.println("***********************");
    }
}

