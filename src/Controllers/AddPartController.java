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
 * FXML Controller class
 * Controller to add part to allParts 
 * @author Kwood
 */
public class AddPartController implements Initializable {
    
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
    private Label compMachIdLbl;
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
        compMachIdLbl.setText("Machine ID");

    }

    /**
     * Changes the label to show Company name.
     * @param event mouse click event on Radio Button
     */
    @FXML
    void onActionOutSourced(ActionEvent event) {
        compMachIdLbl.setText("Company Name");

    }
    /**
     * Cancels adding a new part.
     * Added confirmation so there is no lost progress on accidental click.
     * 
     * @param event Event is a mouse click onto the Cancel button.
     * @throws IOException in case the file is not found
     */
    @FXML
    void onActionCancelNewPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text fields, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }
    /*
    public int generateId(){
        int id = 0;
        for (Part part : Inventory.getAllParts()){
            id++;
        }
        return id;    
    }
    */
    /**
     * Saves the new part upon click.
     * Struggled to stay on this page after an alert pops up.
     * 
     * @param event event is a mouse click on the Save button.
     * @throws IOException in case the Main Screen file isn't found.
     */
    @FXML
    void onActionSaveNewPart(ActionEvent event) throws IOException, InterruptedException {
        
        try{
            int id = 0;
            id = Inventory.getAllParts().size() + 1;
            /**
             * ensures every id is unique
             */
            for(Part part : Inventory.getAllParts()){
                if (part.getId() == id){
                    id++;
                }
            }

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
                /**
                 * Fixed with Scene wait method.
                 */
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
            /*
            if(){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Inventory must be between Min and Max.");
                Optional<ButtonType> result = alert.showAndWait();
                scene.wait();
            }
            */

            if (inHouseRB.isSelected()){

                //compMachIdLbl.setText("Machine ID");
                int machineID = Integer.parseInt(compMachIdTxt.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
            }
            else{

                //compMachIdLbl.setText("Company Name");
                String companyName = compMachIdTxt.getText();
                Inventory.addPart(new Outsource(id, name, price, stock, min, max, companyName));
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
    /*
    public void sendId(Part part){
        idTxt.setText(String.valueOf(part.setId(generateId())));
    }
*/
/**
 * @param url
 * @param rb 
 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
