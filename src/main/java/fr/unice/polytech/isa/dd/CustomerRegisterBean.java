package fr.unice.polytech.isa.dd;

import fr.unice.polytech.isa.dd.entities.Customer;
import fr.unice.polytech.isa.dd.entities.Database;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Stateless(name = "customer-stateless")
public class CustomerRegisterBean implements CustomerRegistration, CustomerFinder {

    @PersistenceContext private EntityManager entityManager;

    @Override
    public Boolean register(String customer_lastname, String customer_firstname, String customer_address) {
        //TODO
        Optional<Customer> customer = findCustomerByName(customer_firstname+" "+customer_lastname);
        if(customer.isPresent()) return false;
        Customer new_customer = new Customer(customer_firstname+" "+customer_lastname,customer_address);
        entityManager.persist(new_customer);
        return true;
        //listcutomer.add(customer);
    }


    @Override
    public Customer findByName(String customer_name) {
        Optional<Customer> customer = findCustomerByName(customer_name);
        return customer.orElse(null);
    }

    public Optional<Customer> findCustomerByName(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
        Root<Customer> root =  criteria.from(Customer.class);
        criteria.select(root).where(builder.equal(root.get("name"), name));
        TypedQuery<Customer> query = entityManager.createQuery(criteria);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException nre){
            return Optional.empty();
        }
    }
}
