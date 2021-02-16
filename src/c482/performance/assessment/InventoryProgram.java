/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482.performance.assessment;
import Model.InHouse;
import Model.Inventory;
import Model.Outsource;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main line
 * @author Kyla Wood
 */


public class InventoryProgram extends Application {

    /**
     *
     * @param stage 
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        
        
        
        Part part1 = new Outsource(1, "Chain", 12.99, 5, 2, 15, "2Chainz");
        Part part2 = new Outsource(2, "Spoke", 10.99, 20, 10, 40, "Spoke To 'Em");
        Part part3 = new InHouse(3, "Frame", 44.99, 5, 2, 15, 347);
        Part part4 = new InHouse(4, "Nipple", 2.99, 15, 10, 40, 179);
        Part part5 = new InHouse(5, "Gear One", 24.99, 7, 2, 20, 258);
        
        Product product1 = new Product(1, "Mountain Bike", 129.99, 3, 1, 8);
        Product product2 = new Product(2, "Street Bike", 159.99, 4, 2, 10);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part3);
        product2.addAssociatedPart(part5);
        product2.addAssociatedPart(part3);
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        
        
        launch(args);
    }
}

