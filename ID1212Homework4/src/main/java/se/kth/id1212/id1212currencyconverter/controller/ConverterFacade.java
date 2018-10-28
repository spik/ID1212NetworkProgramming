
package se.kth.id1212.id1212currencyconverter.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import se.kth.id1212.id1212currencyconverter.model.Converter;

import se.kth.id1212.id1212currencyconverter.integration.ConverterDAO;

@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class ConverterFacade {
    
    @EJB ConverterDAO currencyDB;
    
    public double convert(double amount, String fromName, String toName){
        double fromRate = currencyDB.getCurrencyFromDatabase(fromName).getRate();
        double toRate = currencyDB.getCurrencyFromDatabase(toName).getRate();
        Converter converter = new Converter();
        return converter.convert(amount, fromRate, toRate);
    }
}
