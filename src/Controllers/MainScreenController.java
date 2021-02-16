/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.Inventory;
import static Model.Inventory.getAllParts;
import Model.Part;
import Model.Product;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kwood
 */
public class MainScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
     
    @FXML
    private TextField partSearchTxt;
    
    @FXML
    private TextField productSearchTxt;
    
    @FXML
    private TableView<Product> productTableView;
    
    @FXML
    private TableView<Part> partTableView;
    
    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, Double> productCostCol;
    

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partCostCol;
    
    /**
     * Allows user to add a part.
     * @param event mouse click event.
     * @throws IOException in case add part screen isn't found.
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/AddPart.fxml"));
        loader.load();
        
        AddPartController APController = loader.getController();
        //APController.sendId();
        
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Allows user to modify chosen part.
     * For existing parts I think a drop down of current associated parts would be neat just in case you only want to check.
     * @param event mouse click event
     * @throws IOException in case modify part screen isn't found
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(partTableView.getSelectionModel().getSelectedItem());


            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
        }
        


    }

    /**
     * Deletes chosen part.
     * @param event mouse click event
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete selected part, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
        
            Inventory.removePart(partTableView.getSelectionModel().getSelectedItem());
        }
        
        

    }
    /**
     * Allows user to add a product upon clicking.
     * @param event mouse click event
     * @throws IOException in case add products screen isn't found.
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        
                
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/AddProducts.fxml"));
        loader.load();
        
        AddProductController APController = loader.getController();
        //APController.sendId();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();


    }

    
    
    /**
     * Allows user to modify a product.
     * @param event mouse click event
     * @throws IOException in case modify product screen is not found.
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(productTableView.getSelectionModel().getSelectedItem());



            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
        }
                
        


    }

    /**
     * Deletes product chosen.
     * instead of manually removing parts having the popup suggest removing the parts then deleting.
     * @param event mouse click event
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) throws InterruptedException {
        for(Product product : Inventory.getAllProducts()){
            if (productTableView.getSelectionModel().getSelectedItem().equals(product) && !(product.getAllAssociatedParts().isEmpty())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please remove all parts before deleting.");
                alert.showAndWait();
                scene.wait();
            }
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete selected product, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
        
            Inventory.removeProduct(productTableView.getSelectionModel().getSelectedItem());
        }
        

    }
    
    
    
    
    /*
    public boolean update(int id, Part part){
        int index = -1;
        
        for()
    }*/
    
    /**
     * Code base for search function in regards to ID.
     * @param id part id that you are searching for.
     * @return Return part you are searching for.
     */
    public Part searchPartId(int id){
        
        for(Part part : Inventory.getAllParts()){
            if(part.getId() == id)
                return part;
        }
        
        return null;
    }
    /**
     * Allows user to search for a specific part.
     * @param actionEvent keyboard event on press "Enter."
     */
    public void searchPartName(ActionEvent actionEvent){
        try{
            String searchPart = partSearchTxt.getText();

            Inventory.lookUpPart(searchPart);

            if(Inventory.getAllFilteredParts().isEmpty()){
                int id = Integer.parseInt(searchPart);
                Part part = searchPartId(id);
                if(part != null)
                    Inventory.getAllFilteredParts().add(part);
            }
            
            partTableView.setItems(Inventory.getAllFilteredParts());
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Part not found");
            alert.showAndWait();
        }
        
    }
    /**
     * Code base for search function in regards to product ID.
     * @param prodID product id you are searching for.
     * @return found product id.
     */
    public Product searchProductId(int prodID){
        
        for(Product product : Inventory.getAllProducts()){
            if(product.getProdID() == prodID)
                return product;
        }
        
        return null;
    }
    /**
     * Allows user to search for specific products.
     * @param actionEvent Keyboard "Enter" event
     */
    public void searchProductName(ActionEvent actionEvent){
        try{
            String searchProduct = productSearchTxt.getText();

            Inventory.lookUpProduct(searchProduct);

            if(Inventory.getAllFilteredProducts().isEmpty()){
                int prodID = Integer.parseInt(searchProduct);
                Product product = searchProductId(prodID);
                if(product != null)
                    Inventory.getAllFilteredProducts().add(product);
            }
            
            productTableView.setItems(Inventory.getAllFilteredProducts());
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Product not found");
            alert.showAndWait();
        }
        
    }
    /**
     * Exits the program.
     * @param event mouse click event.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
        
            System.exit(0);
        }
        

    }

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*
        if (partSearchTxt.getText() != null){
            searchPartName(partSearchTxt.getText());
            searchPartId(Integer.valueOf(partSearchTxt.getText()));
            partTableView.setItems(Inventory.getAllFilteredParts());
        }
        else {
            partTableView.setItems(Inventory.getAllParts());
        }
        */
        partTableView.setItems(Inventory.getAllParts());
        
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productTableView.setItems(Inventory.getAllProducts());
        
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("prodID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("prodName"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("prodStock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("prodPrice"));
        
        
        /*
        if(searchPart(1))
            System.out.println("Match!");
        else
            System.out.println("No Match");*/
        
        
    }  


    
}
