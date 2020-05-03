package fr.unice.polytech.isa.dd;

import arquillian.AbstractCustomerRegisterTest;
import fr.unice.polytech.isa.dd.entities.Customer;
import fr.unice.polytech.isa.dd.exceptions.AlreadyExistingCustomerException;
import fr.unice.polytech.isa.dd.exceptions.UnknownCustomerException;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class CustomerRegisterExceptionTest extends AbstractCustomerRegisterTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private UserTransaction userTransaction;

    @EJB(name = "customer-stateless") private CustomerRegistration customerRegistration;
    @EJB(name = "customer-stateless") private CustomerFinder customerFinder;

    private Customer customer1 = new Customer("Messan Aurore","03 Rue soutrane");

    @Before
    public void setUp() {
        entityManager.persist(customer1);
    }

    @After
    public void cleanUp() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        userTransaction.begin();
        Customer customerretrived = entityManager.merge(customer1);
        entityManager.remove(customerretrived);
        userTransaction.commit();
    }

    @Test(expected = AlreadyExistingCustomerException.class)
    public void dontRegisterCustomer() throws AlreadyExistingCustomerException {
        customerRegistration.registerCustomer("Messan","Aurore","3 rue something");
    }

    @Test(expected = UnknownCustomerException.class)
    public void dontfindCustomerByName() throws UnknownCustomerException {
        customerFinder.findCustomerByName("Amoussou");
    }
}
