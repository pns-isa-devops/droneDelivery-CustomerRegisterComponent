package fr.unice.polytech.isa.dd;

import arquillian.AbstractCustomerRegisterTest;
import cucumber.runtime.arquillian.CukeSpace;
import fr.unice.polytech.isa.dd.entities.Customer;
import fr.unice.polytech.isa.dd.entities.Provider;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class CustomerRegisterBeanTest extends AbstractCustomerRegisterTest {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB(name = "customer-stateless") private CustomerRegistration customerRegistration;
    @EJB(name = "customer-stateless") private CustomerFinder customerFinder;

    @Test
    public void register() {

        Customer c = new Customer("Messan Aurore","03 Rue soutrane");
        customerRegistration.register("Aurore","Messan","03 Rue soutrane");
        assertEquals(c.getName(), entityManager.find(Customer.class, c.getId()).getName());
    }

    @Test
    public void findByName() {
        Customer c = new Customer("Amoussou","03 Rue soutrane");
        Customer new_customer = customerFinder.findByName("Amoussou");
        assertNull(new_customer);

        entityManager.persist(c);
        new_customer = customerFinder.findByName("Amoussou");
        assertEquals(new_customer.getName(),c.getName());

    }
}

