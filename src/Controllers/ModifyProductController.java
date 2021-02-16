
package Controllers;

 
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * Controller allowing product modification.
 * FXML Controller class
 *
 * @author Kwood
 */
public class ModifyProductController implements Initializable {

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
    private TableColumn<Part, Integer> associatedPartInventoryLevelCol;
    /**
     * column for associated part costs.
     */
    @FXML
    private TableColumn<Part, Double> associatedPartCostCol;

    /**
     * Adds an associated part to the current product.
     * For next version add in Quantity functionality. Possibly remove from Inv as well.
     * Fixed addAssociatedPart with for loop so associated part goes to the correct product and gets added to the Observable List.
     * @param event Mouse click event
     */
    @FXML
    void onActionAddAssociatedPart(ActionEvent event) {
        for(Product product : Inventory.getAllProducts()){
            if(product.getProdID() == Integer.parseInt(idTxt.getText())){
                
                //product.addAssociatedPart(partTableView.getSelectionModel().getSelectedItem());
                holdingAssociatedParts.add(partTableView.getSelectionModel().getSelectedItem());
            }
        }  
    }
    /**
     * Removes the chosen associated part.
     * @param event Mouse click event
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        
        holdingAssociatedParts.remove(associatedPartTableView.getSelectionModel().getSelectedItem());
    }
    /**
     * On mouse click saves modifications.
     * Separated Product data from part data so Java could process and correctly add parts.
     * @param event mouse click event.
     * @throws IOException in case main screen isn't found.
     * @throws InterruptedException for scene.wait method.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException, InterruptedException {
        
        try{
            int prodID = Integer.parseInt(idTxt.getText());
            String prodName = nameTxt.getText();
            double prodPrice = Double.parseDouble(priceTxt.getText());
            int prodStock = Integer.parseInt(invTxt.getText());
            int prodMin = Integer.parseInt(minTxt.getText());
            int prodMax = Integer.parseInt(maxTxt.getText());
            /*
            FXMLLoader loader = new FXMLLoader();
            loader.load();

            MainScreenController MSController = loader.getController();
            MSController.findIndex();
            */
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
            for(Product product : Inventory.getAllProducts()){
                if(product.getProdID() == prodID){
                    int index;
                    index = Inventory.getAllProducts().indexOf(product);

                    Inventory.updateProduct(index, new Product (prodID, prodName, prodPrice, prodStock, prodMin, prodMax));
                    
                }
            }
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
            
            /*
            for(Product product : Inventory.getAllProducts()){
                if(product.getProdID() == prodID){
                    for(int i = 0; i < product.getAllAssociatedParts().size(); i++){
                        product.getAllAssociatedParts(i);
                    }
                }
            }
            */

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();    
        }
        

        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please put valid input in Text Boxes.");
            alert.showAndWait();
        }   
    }
    /**
     * Cancels modifications to product.
     * @param event mouse click event.
     * @throws IOException in case main screen isn't found.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You will lose any changes, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }
    /**
     * Code base for part ID search function.
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
     * Allows user to search for specific parts.
     * @param actionEvent keyboard action event on press "Enter."
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
     * Retrieves product for modification.
     * Had issues with product associated parts clearing after pressing save.
     * Made a clone of the associated part list so it is more usable and doesn't wipe associated parts.
     * @param product Product to modify
     */
    public void sendProduct(Product product){
        idTxt.setText(String.valueOf(product.getProdID()));
        nameTxt.setText(product.getProdName());
        priceTxt.setText(String.valueOf(product.getProdPrice()));
        invTxt.setText(String.valueOf(product.getProdStock()));
        minTxt.setText(String.valueOf(product.getProdMin()));
        maxTxt.setText(String.valueOf(product.getProdMax()));
       
        holdingAssociatedParts = product.getAllAssociatedParts();
        
        associatedPartTableView.setItems(holdingAssociatedParts);
        
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }
    
    
    /**
     * Initializes the controller
     * @param url
     * @param rb
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
