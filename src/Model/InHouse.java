/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


/**
 * Class InHouse if the part is produced within the company.
 * @author Kyla Wood
 */
public class InHouse extends Part {
    private int machineID;
    
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        
        super(id, name, price, stock, min, max);
        
        this.machineID = machineID;
    }
    /**
     * 
     * @param id machine id to set
     */
    public void setMachineID(int id){
        this.machineID = machineID;
    }
    /**
     * 
     * @return machine ID.
     */
    public int getMachineID(){
        return machineID;
    }
}
 
