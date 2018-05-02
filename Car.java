/*
Student Name: Zheng Sun
Student ID: 1507820
 */
package cse105;

import java.util.*;
import java.text.*;
public class Car {
    private SimpleDateFormat df = new SimpleDateFormat("y/M/d");
    public static int lastID = 0;   
    public static int numOfCarInGarage = 0;
    
    private Date dateOfPurchase;
    private double purchasePrice;
    private int carID;
    
    private Date dateOfSale = new Date();
    private double salePrice = 0;    
    private boolean isSold = false; 
    private double profitOfSale;
    
    private String description = "";
    //Constructor to create Car object by purchasing
    public Car(Date d, double pP, String ds){
            
            this.dateOfPurchase = d;
            this.purchasePrice = pP;
            this.description = ds;
            
            numOfCarInGarage++;
            lastID ++; 
            this.carID = lastID;
            this.profitOfSale = this.salePrice - this.purchasePrice;
    }
    //Constructor to create Car object by reading history deals
    public Car(Date dop, double pP, Date dos, double sP, boolean iS, String ds){
            
            this.dateOfPurchase = dop;
            this.purchasePrice = pP;
            this.dateOfSale = dos;
            this.salePrice = sP;
            this.description = ds;
            lastID ++; 
            this.carID = lastID;
            this.isSold = iS;
            this.profitOfSale = this.salePrice - this.purchasePrice;
            if(this.isSold == false)
                numOfCarInGarage++;
    }
    
    
    public Date getDateOfPurchase(){
        return dateOfPurchase;
    }
    public void setDateOfPurchase(Date x){
        this.dateOfPurchase = x;
    } 
    public double getPurchasePrice(){
        return purchasePrice;
    }
    public void setPurchasePrice(double x){
        this.purchasePrice = x;
        this.profitOfSale = this.salePrice - this.purchasePrice;
    }
    public int getCarID(){
        return carID;
    }
    public void setCarID(int x){
        this.carID = x;
    }
  
    public Date getDateOfSale(){
        return dateOfSale;
    }
    public void setDateOfSale(Date x){
        this.dateOfSale = x;
    }
    public double getSalePrice(){
        return salePrice;
    }
    public void setSalePrice(double x){
        this.salePrice = x;
        this.profitOfSale = this.salePrice - this.purchasePrice;
    }
    public double getProfitOfSale(){
        return this.profitOfSale;
    }
    public boolean isSold(){
        return isSold;
    }
    public void setSold(boolean x){
        this.isSold = x;
        numOfCarInGarage--;
    }
    public String getDesctiption(){
        return description;
    }
    public void setDescription(String x){
        this.description = x;
    }
    //method to flaten a Car object for writen in to a text file
    public String toText(){
    return  this.carID + "," + this.dateOfPurchase.getTime()
            + ","+ this.purchasePrice
            + "," + this.dateOfSale.getTime() + "," + this.salePrice +","+ this.isSold +"," + this.description;
    }
    
    //method to print this object in report format
    public void printReportFormat(){
          System.out.printf("%-5d\t%-15s\t%-11.2f\t%-11s\t%-11.2f\t%-11.2f\t%-25s\n",this.carID
            , df.format(this.dateOfPurchase)
            , this.purchasePrice
            , df.format(this.dateOfSale)
            ,this.salePrice 
            ,this.profitOfSale
            ,this.description
            );
    }
    
    //method to print this object in list format
    public void printListFormat(){
        System.out.printf("%-5d\t%-15s\t%-11.2f\t%-25s\n"
            ,this.carID
            ,df.format(this.dateOfPurchase)
            ,this.purchasePrice
            ,this.description
            );
    }
    
}
