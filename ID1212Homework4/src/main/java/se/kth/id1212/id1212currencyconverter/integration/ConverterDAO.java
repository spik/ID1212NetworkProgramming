
package se.kth.id1212.id1212currencyconverter.integration;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import se.kth.id1212.id1212currencyconverter.model.Currency;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class ConverterDAO {
    @PersistenceContext(unitName = "converterPU")
    private EntityManager em;
    
    /**
     * Finds the currency with the specified name in the database. 
     * @param currencyName The name of the currency that should be fetched from the database
     * @return The currency object that matches the given name
     * @throws EntityNotFoundException If no currency with the specified name exist in the database.
     */
    public Currency getCurrencyFromDatabase(String currencyName){
        
        Currency currency = em.find(Currency.class, currencyName);
        if (currency == null) {
            throw new EntityNotFoundException("Cannot convert to/from currency " + currencyName);
        }
        return currency;
    }
}
