package arquillian;

import fr.unice.polytech.isa.dd.CustomerFinder;
import fr.unice.polytech.isa.dd.CustomerRegisterBean;
import fr.unice.polytech.isa.dd.CustomerRegistration;
import fr.unice.polytech.isa.dd.entities.*;
import fr.unice.polytech.isa.dd.entities.Package;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractCustomerRegisterTest {

    @Deployment
    public static WebArchive createDeployement(){
        return ShrinkWrap.create(WebArchive.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml")
                .addPackage(CustomerRegisterBean.class.getPackage())
                .addPackage(CustomerRegistration.class.getPackage())
                .addPackage(CustomerFinder.class.getPackage())
                .addPackage(Delivery.class.getPackage())
                .addPackage(Customer.class.getPackage())
                .addPackage(Provider.class.getPackage())
                .addPackage(Package.class.getPackage())
                .addPackage(Bill.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                ;
    }
}
