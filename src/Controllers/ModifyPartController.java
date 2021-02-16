/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.InHouse;
import Model.Inventory;
import Model.Outsource;
import Model.Part;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * Controller used to modify chosen part.
 * FXML Controller class
 *
 * @author Kwood
 */
public class ModifyPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    /**
     * Accepts integers for part id is not editable.
     */
    @FXML
    private TextField idTxt;
    /**
     * Accepts strings for part name.
     */
    @FXML
    private TextField nameTxt;
    /**
     * Accepts integers for part inventory level.
     */
    @FXML
    private TextField invTxt;
    /**
     * Accepts doubles for part cost.
     */
    @FXML
    private TextField priceTxt;
    /**
     * Accepts integers for minimum part inventory level.
     */
    @FXML
    private TextField minTxt;
    /**
     * Accepts integers for maximum part inventory level.
     */
    @FXML
    private TextField maxTxt;
    /**
     * Label for the text box, is dependent on the radio button selected.
     */
    @FXML
    private Label inHouseMachIdLbl;
    /**
     * Text box that accepts either a string or integer based on radio button selected.
     */
    @FXML
    private TextField compMachIdTxt;
    /**
     * Toggle group for the radio buttons.
     */
    @FXML
    private ToggleGroup invSource;
    /**
     * Denotes the part as an in house part with a machine id.
     */
    @FXML
    private RadioButton inHouseRB;
    /**
     * Denotes the part as an outsourced part with a company name.
     */
    @FXML
    private RadioButton outsourcedRB;
    
    /**
     * Changes the label to show Machine ID.
     * @param event mouse click event on Radio Button
     */
    @FXML
    void onActionInHouse(ActionEvent event) {
        if (inHouseRB.isSelected()){
            
            inHouseMachIdLbl.setText("Machine ID");
            
        }

    }

    
    /**
     * Changes the label to show Company name.
     * @param event mouse click event on Radio Button
     */
    @FXML
    void onActionOutSource(ActionEvent event) {
        if(outsourcedRB.isSelected()){
            
            inHouseMachIdLbl.setText("Company Name");
            
        }

    }

    /**
     * Cancels modifying part.
     * Added confirmation in case Cancel was accidentally clicked.
     * @param event mouse event when Cancel button is clicked
     * @throws IOException in case Main Screen File is not found.
     */
    @FXML
    void onActionCancelModifyPart(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "All changes will be lost, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Saves the modifications to part.
     * For future check if name is double/int as names typically aren't numerical.
     * added functionality for checking for integers in case Outsourced is accidentally clicked.
     * 
     * @param event mouse event when Save button is clicked.
     * @throws IOException
     * @throws InterruptedException 
     */
    @FXML
    void onActionSaveNewPart(ActionEvent event) throws IOException, InterruptedException {
        try{
        int id = Integer.parseInt(idTxt.getText());
        String name = nameTxt.getText();
        double price = Double.parseDouble(priceTxt.getText());
        int stock = Integer.parseInt(invTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        
        if(min >= max){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Min must be lower than Max");
            alert.showAndWait();
            scene.wait();
            }
        if(min <= 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Min must be more than 0");
            alert.showAndWait();
            scene.wait();
        }
        if(stock > max || stock < min){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Inventory must be between Min and Max.");
            alert.showAndWait();
            scene.wait();
        }
        if (inHouseRB.isSelected()){
            for(Part part : Inventory.getAllParts()){
                if(part.getId() == id){
                    int index = 0;
                    index = Inventory.getAllParts().indexOf(part);
                    

                    int machineID = Integer.parseInt(compMachIdTxt.getText());
                    Inventory.updatePart(index, new InHouse(id, name, price, stock, min, max, machineID));
                }
            }
        }
        
        else{
            for(Part part : Inventory.getAllParts()){
                if(part.getId() == id){
                    int index;
                    index = Inventory.getAllParts().indexOf(part);
                    //compMachIdLbl.setText("Company Name");
                    String companyName = compMachIdTxt.getText();
                    Inventory.updatePart(index, new Outsource(id, name, price, stock, min, max, companyName));
                }
            }
        }
        //Inventory.updatePart(Inventory.getAllParts().indexOf(part), part(id, name, price, stock, min, max));
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
        }
        /*
        catch(IndexOutOfBoundsException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please put valid input in Text Boxes.");
            alert.showAndWait();
        }
        */
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please put valid input in Text Boxes.");
            alert.showAndWait();
        }
        //Part part;
        


    }

    /**
     * Sets up the text boxes for this view.
     * For functionality maybe make the table more interactive so you're able to update one piece of part (i.e. inv level) without changing screens.
     * @param part Part that is going to be updated.
     */
    public void sendPart(Part part){
        idTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        priceTxt.setText(String.valueOf(part.getPrice()));
        invTxt.setText(String.valueOf(part.getStock()));
        minTxt.setText(String.valueOf(part.getMin()));
        maxTxt.setText(String.valueOf(part.getMax()));
        
        if (part instanceof InHouse){
            inHouseRB.setSelected(true);
            inHouseMachIdLbl.setText("Machine ID");
            compMachIdTxt.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
        else if (part instanceof Outsource){
            outsourcedRB.setSelected(true);
            inHouseMachIdLbl.setText("Company Name");
            compMachIdTxt.setText(((Outsource) part).getCompanyName());
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
        //idTxt = getId;
        
        
    }    

    
}
