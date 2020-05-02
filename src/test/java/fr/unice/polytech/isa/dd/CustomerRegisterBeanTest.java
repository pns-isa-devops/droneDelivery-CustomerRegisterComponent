package fr.unice.polytech.isa.dd;

import arquillian.AbstractCustomerRegisterTest;
import cucumber.runtime.arquillian.CukeSpace;
import fr.unice.polytech.isa.dd.entities.Customer;
import fr.unice.polytech.isa.dd.entities.Provider;
import fr.unice.polytech.isa.dd.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.isa.dd.exceptions.UnknownCustomerException;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class CustomerRegisterBeanTest extends AbstractCustomerRegisterTest {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB(name = "customer-stateless") private CustomerRegistration customerRegistration;
    @EJB(name = "customer-stateless") private CustomerFinder customerFinder;
    @Inject private UserTransaction userTransaction;

    private Customer customer1 = new Customer("Messan Aurore","03 Rue soutrane");
    private Customer customer2 = new Customer("Amoussou","03 Rue soutrane");

    @After
    public void cleanUp() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException, UnknownCustomerException {
        userTransaction.begin();
        Customer customerretrived2 = entityManager.merge(customer2);
        entityManager.remove(customerretrived2);
        userTransaction.commit();
    }

    @Test
    public void register() throws AlreadyExistingCustomerException, UnknownCustomerException {
        customerRegistration.register("Messan","Aurore","03 Rue soutrane");
        assertEquals(customer1.getName(), entityManager.find(Customer.class, customer1.getId()).getName());
        customer1 = customerFinder.findCustomerByName("Messan Aurore");
        customer1 = entityManager.find(Customer.class, customer1.getId());
        entityManager.remove(customer1);
    }

    @Test
    public void findByName() throws UnknownCustomerException {
        entityManager.persist(customer2);
        Customer new_customer = customerFinder.findCustomerByName("Amoussou");
        assertEquals(new_customer.getName(),customer2.getName());
    }
}

