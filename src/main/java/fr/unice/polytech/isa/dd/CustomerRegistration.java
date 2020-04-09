package fr.unice.polytech.isa.dd;

import javax.ejb.Local;

@Local
public interface CustomerRegistration {

     Boolean register(String customer_lastname,String customer_firstname,String customer_address );
}
