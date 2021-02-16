/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import Controllers.MainScreenController;
import Controllers.ModifyProductController;
import Model.Part;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class Inventory to host all inventory parts and products along with some of their interactions.
 * Also supplements some functions in relation to parts and products.
 * @author Kyla Wood
 */
public class Inventory {
    /**
     * Hosts all parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Adds part to all parts
     * @param part Adds in specified part to the rest of the parts.
     */
    public static void addPart(Part part){
        allParts.add(part);
    }
    /**
     * Retrieves the all parts list.
     * @return Returns all Parts.
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /**
     * Hosts all products.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * Adds product to all products.
     * @param product Adds in specified products to the rest of the products.
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    /**
     * Retrieves all products.
     * @return Returns all products.
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
    /**
     * Hosts all "found" parts.
     */
    public static ObservableList<Part> lookupPart = FXCollections.observableArrayList();
    /**
     * Retrieves all "found" parts
     * @return Returns all parts from a search function.
     */
    public static ObservableList<Part> getAllFilteredParts(){
        return lookupPart;
    }
    /**
     * Hosts all "found" products.
     */
    public static ObservableList<Product> lookupProduct = FXCollections.observableArrayList();
    /**
     * Retrieves all "found" products.
     * @return Returns all products from a search function.
     */
    public static ObservableList<Product> getAllFilteredProducts(){
        return lookupProduct;
    }
    /**
     * Function for that does the work to search for the part.
     * Had issues with notification of unfound part(would alert regardless of part found or not),
     * Fixed by moving alert into a catch/try block in the main screen controller.
     * @param partName Part that you wish to find.
     * @return Return a list of all found parts
     */
    public static ObservableList<Part> lookUpPart (String partName){
        
        if(!(Inventory.getAllFilteredParts()).isEmpty()){
            Inventory.getAllFilteredParts().clear();
        }
        /**
         * for universal searching regardless of upper/lower case.
         */
        partName = partName.toLowerCase();
        for(Part part : Inventory.getAllParts()){
            if(part.getName().toLowerCase().contains(partName))
                Inventory.getAllFilteredParts().add(part);
        }
        if(Inventory.getAllFilteredParts().isEmpty()){
            
            return Inventory.getAllParts();
        }
        else
            return Inventory.getAllFilteredParts();
    }
    /**
     * Function for that does the work to search for the product.
     * See lookUpPart for troubleshooting.
     * For additional functionality perhaps adding a "did you mean..." window if spelling is close.
     * @param productName Product you would like to find.
     * @return Returns all found products.
     */
    public static ObservableList<Product> lookUpProduct (String productName){
        if(!(Inventory.getAllFilteredProducts()).isEmpty()){
            Inventory.getAllFilteredProducts().clear();
        }
        
        productName = productName.toLowerCase();
        for(Product product : Inventory.getAllProducts()){
            if(product.getProdName().toLowerCase().contains(productName))
                Inventory.getAllFilteredProducts().add(product);
            
        }
        if(Inventory.getAllFilteredProducts().isEmpty()){
           
            return Inventory.getAllProducts();
        }
        else
            return Inventory.getAllFilteredProducts();
    }
    /**
     * Function that does the work to update the product.
     * See updatePart for troubleshooting.
     * 
     * @param index Index at which the product resides that you wish to change
     * @param newProduct The updated product that will be put in at index.
     */
    public static void updateProduct(int index, Product newProduct){
        
        Inventory.getAllProducts().set(index, newProduct);
        /*
        for(Part part : newProduct.getAllAssociatedParts()){
            newProduct.addAssociatedPart(part);
        }
        */
    }
    /**
     * Function that does the work to update the part.
     * @param index Index at which the part resides that you wish to change.
     * @param newPart The updated part that will be put in at index.
     */
    public static void updatePart(int index, Part newPart){
        
        /** 
        * Looping logic caused all parts to be modified instead of just one.
        * Fixed by removing loop and only using set method.
        */
        /*
        
        int id = 0;
        for(Part part : Inventory.getAllParts()){
            index++;
            id++;
            //if(Inventory.getAllParts().indexOf(part) == index){
            if(part.getId() == id){
                Inventory.getAllParts().set(index, newPart);
            }
        }
*/        

        Inventory.getAllParts().set(index, newPart);
    }
    /**
     * Function that does the work to remove selected product.
     * @param selectedProduct product the user wishes to remove.
     * @return boolean of true if removed and false if not.
     */
    public static boolean removeProduct(Product selectedProduct){
        for(Product product : Inventory.getAllProducts()){
            if(product == selectedProduct){
                allProducts.remove(selectedProduct);
                break;
                //return true;
            }
            
        }
        return false;
    }
    /**
     * Function that does the work to remove selected part.
     * @param selectedPart part the user wishes to remove.
     * @return boolean of true if removed and false if not.
     */
    public static boolean removePart(Part selectedPart){
        for(Part part : Inventory.getAllParts()){
            if(part == selectedPart){
                allParts.remove(selectedPart);
                break;
                //return true;
            }
            
        }
        return false;
    }
    
    
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
