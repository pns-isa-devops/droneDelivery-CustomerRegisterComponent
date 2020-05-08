package fr.unice.polytech.isa.dd;

import fr.unice.polytech.isa.dd.entities.Customer;
import fr.unice.polytech.isa.dd.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.isa.dd.exceptions.UnknownCustomerException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless(name = "customer-stateless")
public class CustomerRegisterBean implements CustomerRegistration, CustomerFinder {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Boolean registerCustomer(String customer_lastname, String customer_firstname, String customer_address) throws AlreadyExistingCustomerException {
        Optional<Customer> customer = findCustomerByNameInDatabase(customer_lastname + " " + customer_firstname);
        if (customer.isPresent()) {
            throw new AlreadyExistingCustomerException(customer_lastname + " " + customer_firstname);
        }
        Customer new_customer = new Customer(customer_lastname + " " + customer_firstname, customer_address);
        entityManager.persist(new_customer);
        return true;
    }


    @Override
    public Customer findCustomerByName(String customer_name) throws UnknownCustomerException {
        Optional<Customer> customer = findCustomerByNameInDatabase(customer_name);
        if (customer.isPresent()) {
            return customer.get();
        } else throw new UnknownCustomerException(customer_name);
    }

    private Optional<Customer> findCustomerByNameInDatabase(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
        Root<Customer> root = criteria.from(Customer.class);
        criteria.select(root).where(builder.equal(root.get("name"), name));
        TypedQuery<Customer> query = entityManager.createQuery(criteria);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    private List<Customer> allCustomer() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
        Root<Customer> root = criteria.from(Customer.class);
        criteria.select(root);
        TypedQuery<Customer> query = entityManager.createQuery(criteria);
        try {
            List<Customer> toReturn = new ArrayList<>(query.getResultList());
            return Optional.of(toReturn).get();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public Boolean deleteAll() {
        //   recupérer tous les clients
        List<Customer> allCustomer = allCustomer();
        for (Customer c : allCustomer) {
            entityManager.remove(c);
        }
        return true;
    }
}
