/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Class Product is composed of Parts
 * @author Kyla Wood
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private int prodID;
    private String prodName;
    private double prodPrice;
    private int prodStock;
    private int prodMin;
    private int prodMax;
    
    public Product(int prodID, String prodName, double prodPrice, int prodStock, int prodMin, int prodMax){
            this.associatedParts = FXCollections.observableArrayList();
            this.prodID = prodID;
            this.prodName = prodName;
            this.prodPrice = prodPrice;
            this.prodStock = prodStock;
            this.prodMin = prodMin;
            this.prodMax = prodMax;
        }
    
    /**
     * @return the product ID.
     */
    
    public int getProdID(){
        return prodID;
    }
    
    /**
     * @param prodID the product ID to set.
     */
    public void setProdID(int prodID){
        this.prodID = prodID;
    }
    
     /**
     * @return the product name.
     */
    
    public String getProdName(){
        return prodName;
    }
    
    /**
     * @param prodName the product name to set.
     */
    public void setProdName(String prodName){
        this.prodName = prodName;
    }
    
     /**
     * @return the product price.
     */
    
    public double getProdPrice(){
        return prodPrice;
    }
    
    /**
     * @param prodPrice the product price to set.
     */
    public void setProdPrice(double prodPrice){
        this.prodPrice = prodPrice;
    }
    
     /**
     * @return the product stock.
     */
    
    public int getProdStock(){
        return prodStock;
    }
    
    /**
     * @param prodStock the product stock to set.
     */
    public void setProdStock(int prodStock){
        this.prodStock = prodStock;
    }
    
     /**
     * @return the product minimum.
     */
    
    public int getProdMin(){
        return prodMin;
    }
    
    /**
     * @param prodMin the product minimum to set.
     */
    public void setProdMin(int prodMin){
        this.prodMin = prodMin;
    }
    
     /**
     * @return the product maximum.
     */
    
    public int getProdMax(){
        return prodMax;
    }
    
    /**
     * @param prodMax the product maximum to set.
     */
    public void setProdMax(int prodMax){
        this.prodMax = prodMax;
    }
    /**
     * Retrieves all associated parts.
     * @return All associated parts with this product.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    /**
     * Adds an associated part
     * @param part Part that is being added to this product.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Removes an associated part.
     * @param selectedAssociatedPart Part you wish to remove.
     * @return a boolean of true if deleted and false if not.
     */
    public boolean removeAssociatedPart(Part selectedAssociatedPart){
        for(Part part : getAllAssociatedParts()){
            if(part == selectedAssociatedPart){
                associatedParts.remove(selectedAssociatedPart);
            }
            
        }
        return false;
    }
} 
    


