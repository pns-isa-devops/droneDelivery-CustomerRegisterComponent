package fr.unice.polytech.isa.dd;

import fr.unice.polytech.isa.dd.exceptions.AlreadyExistingCustomerException;

import javax.ejb.Local;

@Local
public interface CustomerRegistration {

     Boolean registerCustomer(String customer_lastname,String customer_firstname,String customer_address ) throws AlreadyExistingCustomerException;
}
