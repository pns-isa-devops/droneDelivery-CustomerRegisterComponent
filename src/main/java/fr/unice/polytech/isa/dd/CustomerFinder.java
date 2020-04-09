package fr.unice.polytech.isa.dd;

import fr.unice.polytech.isa.dd.entities.Customer;

import javax.ejb.Local;

@Local
public interface CustomerFinder {

    Customer findByName(String customer_name);
}
