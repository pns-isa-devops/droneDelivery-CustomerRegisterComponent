package fr.unice.polytech.isa.dd;

import fr.unice.polytech.isa.dd.entities.Customer;
import fr.unice.polytech.isa.dd.exceptions.UnknownCustomerException;

import javax.ejb.Local;

@Local
public interface CustomerFinder {

    Customer findCustomerByName(String customer_name) throws UnknownCustomerException;
}
