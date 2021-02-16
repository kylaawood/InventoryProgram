/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;


import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
 * Controller used to add a product to all products.
 * FXML Controller class
 *
 * @author Kwood
 */
public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;
    
    /**
     * To hold associated parts so they can be added to the product
     */
    private ObservableList<Part> holdingAssociatedParts = FXCollections.observableArrayList();
    /**
     * Where the user will enter their desired search
     */
    @FXML
    private TextField searchBar;
    /**
     * Accepts integers is non editable.
     */
    @FXML
    private TextField idTxt;
    /**
     * Accepts strings for the product name.
     */
    @FXML
    private TextField nameTxt;
    /**
     * Accepts integers for the amount of products.
     */
    @FXML
    private TextField invTxt;
    /**
     * Accepts doubles for the price of products.
     */
    @FXML
    private TextField priceTxt;
    /**
     * Accepts integers for the maximum allowable product.
     */
    @FXML
    private TextField maxTxt;
    /**
     * Accepts integers for the minimum amount of product. 
     */
    @FXML
    private TextField minTxt;
    /**
     * Table view for all parts.
     */
    @FXML
    private TableView<Part> partTableView;
    /**
     * Table view for associated parts.
     */
    @FXML
    private TableView<Part> associatedPartTableView;
    /**
     * Column for all part ids.
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    /**
     * Column for all part names.
     */
    @FXML
    private TableColumn<Part, String> partNameCol;
    /**
     * column for all part inventory levels.
     */
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    /**
     * Column for all part costs.
     */
    @FXML
    private TableColumn<Part, Double> partCostCol;
    /**
     * Column for associated part ids.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;
    /**
     * Column for associated part names.
     */
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;
    /**
     * column for associated part inventory levels.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;
    /**
     * column for associated part costs.
     */
    @FXML
    private TableColumn<Part, Double> associatedPartCostCol;
    
    /**
     * Adds an associated part to the new product.
     * Once again for added functionality I think a quantity:__ popup box would be good.
     * split table views onto buttons so they refresh every press.
     * @param event mouse click event.
     */
    @FXML
    void onActionAddAssociatedPart(ActionEvent event) {
        
        
        holdingAssociatedParts.add(partTableView.getSelectionModel().getSelectedItem());
        
        associatedPartTableView.setItems(holdingAssociatedParts);
        
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     * Removes an associated part.
     * @param event mouse click event.
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        
        
        holdingAssociatedParts.remove(associatedPartTableView.getSelectionModel().getSelectedItem());
        associatedPartTableView.setItems(holdingAssociatedParts);
        
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        

    }
    /**
     * Saves the new product upon mouse click.
     * For loop to check id's to ensure a unique id.
     * For loop Product product to ensure the proper product receives its associated parts 
     * @param event mouse click event.
     * @throws InterruptedException for scene.wait functionality.
     * @throws IOException in case Main Screen isn't found.
     */
    @FXML
    void onActionSaveNewProduct(ActionEvent event) throws InterruptedException, IOException {
        
        try{
            int prodID = 0;
            prodID = Inventory.getAllProducts().size() + 1;
            
            for(Product product : Inventory.getAllProducts()){
                    if (product.getProdID() == prodID){
                        prodID++;
                    }
                }
            String prodName = nameTxt.getText();
            double prodPrice = Double.parseDouble(priceTxt.getText());
            int prodStock = Integer.parseInt(invTxt.getText());
            int prodMin = Integer.parseInt(minTxt.getText());
            int prodMax = Integer.parseInt(maxTxt.getText());

            if(prodMin >= prodMax){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Min must be lower than Max");
                alert.showAndWait();
                scene.wait();
                }
            if(prodMin <= 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Min must be more than 0");
                alert.showAndWait();
                scene.wait();
            }
            if(prodStock > prodMax || prodStock < prodMin){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Inventory must be between Min and Max.");
                alert.showAndWait();
                scene.wait();
            }
            if(holdingAssociatedParts.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Product must have at least one associated part.");
                    alert.showAndWait();
                    scene.wait();
                }
            Inventory.addProduct(new Product(prodID, prodName, prodPrice, prodStock, prodMin, prodMax));
            /**
             * 
             */
            for(Product product : Inventory.getAllProducts()){
                    if(product.getProdID() == prodID){
                        for(Part part : holdingAssociatedParts){
                            product.addAssociatedPart(part);
                        }
                    }
                }

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid input for each text field.");
            alert.showAndWait();
        }
    }
    /**
     * Cancels adding a new part.
     * @param event mouse event
     * @throws IOException in case main screen isn't found
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text fields, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }


    }
    /**
     * Code base for part ID search.
     * @param id id you want to find.
     * @return found id.
     */
    public Part searchPartId(int id){
        
        for(Part part : Inventory.getAllParts()){
            if(part.getId() == id)
                return part;
        }
        
        return null;
    }
    /**
     * Allows user to search for specific parts.
     * added action event to search bar to remove button.
     * @param actionEvent keyboard event on "Enter."
     */
    public void searchPartName(ActionEvent actionEvent){
        try{
            String searchPart = searchBar.getText();

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
     * 
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        partTableView.setItems(Inventory.getAllParts());
        
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
    }    
    
}
