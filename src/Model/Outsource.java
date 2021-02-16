/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Outsource if the part is produced outside of the company and brought in.
 * @author Kyla Wood
 */


public class Outsource extends Part {
    
    private String companyName;
    
        public Outsource(int id, String name, double price, int stock, int min, int max, String companyName){
            
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * 
     * @param name Name of company to set.
     */
    public void setCompanyName(String name){
        this.companyName = name;
    }
    /**
     * 
     * @return Return name of company.
     */
    public String getCompanyName(){
        return companyName;
    }
    
}

