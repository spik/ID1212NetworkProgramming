
package se.kth.id1212.id1212currencyconverter.model;

/**
 * Handles the conversion between currencies
 */
public class Converter {
    
    /**
     * Converts an amount from one currency to another
     * @param amount The amount that is to be converted to another currency
     * @param fromRate The rate of the currency that the amount is to be converted from
     * @param toRate The rate of the currency that the amount is to be converted to
     * @return The converted amount, i.e. the value of the amount in the sencond concurrency 
     */
    public double convert(double amount, double fromRate, double toRate){
        return amount * (fromRate / toRate);
    }
}
