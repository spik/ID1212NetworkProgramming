/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id1212.id1212currencyconverter.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Currency implements Serializable{
    
    @Id
    private String name;
    private double rate;
    
    public Currency(){
        
    }
    
    public Currency (String name, double rate){
        this.name = name;
        this.rate = rate;
    }
    
    public String getName(){
        return name;
    }
    
    public double getRate(){
        return rate;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setRate(double rate){
        this.rate = rate;
    }
}
