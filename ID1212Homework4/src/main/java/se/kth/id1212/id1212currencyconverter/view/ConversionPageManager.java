
package se.kth.id1212.id1212currencyconverter.view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import se.kth.id1212.id1212currencyconverter.controller.ConverterFacade;

@Named("convManager")
@RequestScoped
public class ConversionPageManager implements Serializable{
    @EJB
    private ConverterFacade converterFacade;
    private Exception convertionFailure;
    private double convertedAmount;
    private double amount;
    private String toCurrency;
    private String fromCurrency;
    
    private void handleException(Exception e) {
        e.printStackTrace(System.err);
        convertionFailure = e;
    }
    
    public boolean getSuccess() {
        return convertionFailure == null;
    }
    
    public Exception getException() {
        return convertionFailure;
    }
    
    public void convert() {
        try {
            convertionFailure = null;
            convertedAmount = converterFacade.convert(amount, fromCurrency, toCurrency);
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    public double getConvertedAmount(){
        return convertedAmount;
    }
    
    public void setFromCurrency(String fromCurrency){
        this.fromCurrency = fromCurrency;
    }
    
    public void setToCurrency(String toCurrency){
        this.toCurrency = toCurrency;
    }
    
    public void setAmount(double amount){
        this.amount = amount;
    }
    
    public String getFromCurrency(){
        return fromCurrency;
    }
    
    public String getToCurrency(){
        return toCurrency;
    }
    
    public double getAmount(){
        return amount;
    }
}
